package pioneer.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import pioneer.common.dto.ResponseResult;
import pioneer.common.enums.AppHttpCodeEnum;
import pioneer.common.util.AppJwtUtil;
import pioneer.common.util.FileUtils;
import pioneer.user.dto.LoginDto;
import pioneer.user.entity.ApUser;
import pioneer.user.mapper.ApUserMapper;
import pioneer.user.service.IApUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * APP用户信息表 服务实现类
 */
@Service
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements IApUserService {

    @Override
    public ResponseResult login(LoginDto dto) {

        //判断手机号和密码是否为空，为空就是不登录先看看
        if(StringUtils.isNotBlank(dto.getPhone())&&StringUtils.isNotBlank(dto.getPassword())){
            LambdaQueryWrapper<ApUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ApUser::getPhone,dto.getPhone());
            ApUser one = this.getOne(queryWrapper);
            if (one==null){
                return ResponseResult.errorResult(AppHttpCodeEnum.USER_NOT_EXIST);
            }

            //比对密码
            String s = DigestUtils.md5Hex(dto.getPassword() + one.getSalt());
            if (!StringUtils.equals(one.getPassword(),s)){
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }
            //构建返回结果
            Map resultMap = new HashMap();
            //去掉隐私信息
            one.setPassword("");
            one.setSalt("");
            resultMap.put("user",one);
            //jwt生成token
            Map<String, Object> claimMaps = new HashMap<>();
            claimMaps.put("userId",one.getId());
            claimMaps.put("name",one.getName());
            String token = AppJwtUtil.getToken(claimMaps);
            resultMap.put("token",token);

            return ResponseResult.okResult(resultMap);
        }else {
//            不登录先看看(用0作为用户id)

            //构建返回结果
            Map resultMap = new HashMap();
            ApUser apUser = new ApUser();
            //去掉隐私信息
            apUser.setId(0);
            apUser.setName(dto.getEquipmentId()); //防止所有人都用不登录导致是同一个id0，这里加上设备id作为区分
            resultMap.put("user",apUser);

            //jwt生成token
            Map<String, Object> claimMaps = new HashMap<>();
            claimMaps.put("userId",apUser.getId());
            claimMaps.put("name",apUser.getName());
            String token = AppJwtUtil.getToken(claimMaps);
            resultMap.put("token",token);

            return ResponseResult.okResult(resultMap);
        }
    }
}
