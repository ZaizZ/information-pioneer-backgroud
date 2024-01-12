package pioneer.media.service;

import org.springframework.web.multipart.MultipartFile;
import pioneer.common.dto.ResponseResult;
import pioneer.media.dto.SensitiveDto;
import pioneer.media.entity.WmSensitive;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 敏感词信息表 服务类
 */
public interface IWmSensitiveService extends IService<WmSensitive> {

    ResponseResult upload(MultipartFile file) throws IOException;

    ResponseResult download(HttpServletResponse response) throws Exception;

    ResponseResult listByName(SensitiveDto dto);
}
