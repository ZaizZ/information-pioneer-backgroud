package pioneer.media.service;

import pioneer.common.dto.ResponseResult;
import pioneer.media.dto.LoginDto;
import pioneer.media.entity.WmUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 自媒体用户信息表 服务类
 */
public interface IWmUserService extends IService<WmUser> {

    ResponseResult<WmUser> saveWmUser(WmUser wmUser);

    ResponseResult login(LoginDto dto);
}
