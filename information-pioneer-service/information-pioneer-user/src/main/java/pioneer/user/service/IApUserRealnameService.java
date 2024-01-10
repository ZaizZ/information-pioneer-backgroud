package pioneer.user.service;

import pioneer.common.dto.ResponseResult;
import pioneer.user.dto.AuthDto;
import pioneer.user.entity.ApUserRealname;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * APP实名认证信息表 服务类
 */
public interface IApUserRealnameService extends IService<ApUserRealname> {

    ResponseResult listByStatus(AuthDto dto);

    ResponseResult auth(AuthDto dto, int flag);
}
