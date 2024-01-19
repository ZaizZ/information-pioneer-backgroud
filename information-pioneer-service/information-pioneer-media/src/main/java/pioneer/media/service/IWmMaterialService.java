package pioneer.media.service;

import org.springframework.web.multipart.MultipartFile;
import pioneer.common.dto.ResponseResult;
import pioneer.media.dto.WmMaterialDto;
import pioneer.media.entity.WmMaterial;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 自媒体图文素材信息表 服务类
 */
public interface IWmMaterialService extends IService<WmMaterial> {

    ResponseResult<WmMaterial> saveWmMaterial(MultipartFile file);

    ResponseResult listByStatus(WmMaterialDto dto);

    ResponseResult collection(Integer id, int type);

    ResponseResult deleteMaterial(Integer id);
}
