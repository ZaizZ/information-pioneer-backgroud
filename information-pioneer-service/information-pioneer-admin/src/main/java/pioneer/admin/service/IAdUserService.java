package pioneer.admin.service;

import pioneer.admin.dto.LoginDto;
import pioneer.admin.entity.AdUser;
import com.baomidou.mybatisplus.extension.service.IService;
import pioneer.common.dto.ResponseResult;

/**
 * 管理员用户信息表 服务类
 */
public interface IAdUserService extends IService<AdUser> {

    ResponseResult login(LoginDto dto);
}
