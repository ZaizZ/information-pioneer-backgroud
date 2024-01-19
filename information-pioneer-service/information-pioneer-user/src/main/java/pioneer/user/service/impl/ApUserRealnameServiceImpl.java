package pioneer.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pioneer.common.dto.PageResponseResult;
import pioneer.common.dto.ResponseResult;
import pioneer.common.enums.AppHttpCodeEnum;
import pioneer.user.client.ArticleFeign;
import pioneer.user.client.MediaFeign;
import pioneer.user.dto.ApAuthor;
import pioneer.user.dto.AuthDto;
import pioneer.user.dto.WmUser;
import pioneer.user.entity.ApUser;
import pioneer.user.entity.ApUserRealname;
import pioneer.user.mapper.ApUserRealnameMapper;
import pioneer.user.service.IApUserRealnameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pioneer.user.service.IApUserService;

import java.util.Date;

/**
 * APP实名认证信息表 服务实现类
 */
@Service
public class ApUserRealnameServiceImpl extends ServiceImpl<ApUserRealnameMapper, ApUserRealname> implements IApUserRealnameService {

    @Autowired
    private ArticleFeign articleFeign;

    @Autowired
    private MediaFeign mediaFeign;

    @Autowired
    private IApUserService apUserService;

    @Override
    public ResponseResult listByStatus(AuthDto dto) {
        if(dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        LambdaQueryWrapper<ApUserRealname> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dto.getStatus()!=null,ApUserRealname::getStatus,dto.getStatus());

        IPage<ApUserRealname> page = new Page<>(dto.getPage(), dto.getSize());
        IPage<ApUserRealname> result = this.page(page,queryWrapper);

        return new PageResponseResult(dto.getPage(),dto.getSize(),page.getTotal(),result.getRecords());
    }

    @Override
    @GlobalTransactional //分布式事务
    public ResponseResult auth(AuthDto dto, int flag) {

        LambdaQueryWrapper<ApUserRealname> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dto.getId()!=null,ApUserRealname::getUserId,dto.getId());

        ApUserRealname one = this.getOne(queryWrapper);
        if (one == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //驳回
        if (flag == 0){
            one.setStatus(2);
            if(StringUtils.isNotBlank(dto.getMsg())){
                one.setReason(dto.getMsg());
            }else{
                one.setReason(dto.getReason());
            }
            one.setUpdatedTime(new Date());
            this.updateById(one);
        }else{
            //审核通过
            //feign远程调用自媒体微服务，插入一条自媒体人数据
            ApUser apUser = apUserService.getById(dto.getId());
            WmUser wmUser = createWmUser(apUser);

            //远程调用文章微服务，插入一条自媒体人数据
            ApAuthor apAuthor = createApAuthor(apUser,wmUser.getId());

            //更新自媒体人中的apAuthorId数据
            wmUser.setApAuthorId(apAuthor.getId());
            ResponseResult responseResult = mediaFeign.updateWmUser(wmUser);
            if (responseResult.getCode()!=0) {
                throw new RuntimeException("远程调用失败");
            }

            //修改ApUserRealname的状态和Apuser的相关标识
            one.setStatus(9);
            one.setUpdatedTime(new Date());
            this.updateById(one);

            apUser.setIsIdentityAuthentication(true);
            apUser.setFlag(1);
            apUserService.updateById(apUser);

        }
        return ResponseResult.okResult();
    }

    private ApAuthor createApAuthor(ApUser apUser,Integer wmUserId) {
        ApAuthor apAuthor = new ApAuthor();
        apAuthor.setName(apUser.getName());
        apAuthor.setType(2);
        apAuthor.setUserId(apUser.getId());
        apAuthor.setWmUserId(wmUserId);
        apAuthor.setCreatedTime(new Date());
        ResponseResult<ApAuthor> author = articleFeign.saveApAuthor(apAuthor);
        if (author.getCode()!=0){
            throw new RuntimeException("远程调用失败");
        }
        return author.getData();
    }

    private WmUser createWmUser(ApUser apUser) {
        WmUser wmUser = new WmUser();
        wmUser.setApUserId(apUser.getId());
//            wmUser.setApAuthorId(0);
        wmUser.setName(apUser.getName());
        wmUser.setPassword(apUser.getPassword());
        wmUser.setSalt(apUser.getSalt());
//            wmUser.setNickname();
        wmUser.setImage(apUser.getImage());
//            wmUser.setLocation();
        wmUser.setPhone(apUser.getPhone());
        wmUser.setStatus(apUser.getStatus());
//            wmUser.setEmail("");
        wmUser.setType(0);
        wmUser.setScore(0);
//            wmUser.setLoginTime(new Date());
            wmUser.setCreatedTime(new Date());
        ResponseResult<WmUser> responseResult = mediaFeign.saveWmUser(wmUser);
        if (responseResult.getCode()!=0) {
            throw new RuntimeException("远程调用失败");
        }
        return responseResult.getData();
    }
}
