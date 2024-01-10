package pioneer.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import pioneer.article.entity.ApAuthor;
import pioneer.article.mapper.ApAuthorMapper;
import pioneer.article.service.IApAuthorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pioneer.common.dto.ResponseResult;
import pioneer.common.enums.AppHttpCodeEnum;

import java.util.Date;

/**
 * APP文章作者信息表 服务实现类
 */
@Service
public class ApAuthorServiceImpl extends ServiceImpl<ApAuthorMapper, ApAuthor> implements IApAuthorService {

    @Override
    public ResponseResult<ApAuthor> saveApAuthor(ApAuthor apAuthor) {
        if (apAuthor == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //判断是否已经是作者
        LambdaQueryWrapper<ApAuthor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(apAuthor.getUserId()!=null,ApAuthor::getUserId,apAuthor.getUserId());
        ApAuthor one = this.getOne(queryWrapper);
        if (one == null){
            //不是作者，直接保存
            apAuthor.setCreatedTime(new Date());
            this.save(apAuthor);
            return ResponseResult.okResult(apAuthor);
        }else{
            return ResponseResult.okResult(one);
        }
    }
}
