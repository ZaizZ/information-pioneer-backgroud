package pioneer.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import pioneer.admin.dto.LoginDto;
import pioneer.admin.entity.AdUser;
import pioneer.admin.mapper.AdUserMapper;
import pioneer.admin.service.IAdUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pioneer.common.dto.ResponseResult;
import pioneer.common.enums.AppHttpCodeEnum;
import pioneer.common.util.AppJwtUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员用户信息表 服务实现类
 */
@Service
public class AdUserServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements IAdUserService {

    @Override
    public ResponseResult login(LoginDto dto) {
        //用户名和密码不能为空
        if (StringUtils.isBlank(dto.getName())||StringUtils.isBlank(dto.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.NAME_PASSWORD_NOTNULL);
        }
        LambdaQueryWrapper<AdUser> queryWrapper = new LambdaQueryWrapper<>();
        //先根据用户名查询
        queryWrapper.eq(AdUser::getName,dto.getName());
        AdUser one = this.getOne(queryWrapper);
        if (one==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_NOT_EXIST);
        }
        //比较密码 密码+盐 再加密，然后和数据库中的密码进行比较
        String password = one.getPassword(); //数据库中加密的密文
        String password1 = dto.getPassword(); //页面接受的明文
        password1 = DigestUtils.md5Hex(password1 + one.getSalt());
        if (!StringUtils.equals(password,password1)){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        Map resultMap = new HashMap();
        //去掉隐私信息
        one.setPassword("");
        one.setSalt("");
        one.setPhone("");
        resultMap.put("user",one);

        Map<String, Object> claimMaps = new HashMap<>();
        claimMaps.put("userId",one.getId());
        claimMaps.put("name",one.getName());
        String token = AppJwtUtil.getToken(claimMaps);
        resultMap.put("token",token);

        return ResponseResult.okResult(resultMap);


    }
}
