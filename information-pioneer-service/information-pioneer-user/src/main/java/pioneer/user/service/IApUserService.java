package pioneer.user.service;

import pioneer.common.dto.ResponseResult;
import pioneer.user.dto.LoginDto;
import pioneer.user.entity.ApUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * APP用户信息表 服务类
 */
public interface IApUserService extends IService<ApUser> {

    ResponseResult login(LoginDto dto);
}
