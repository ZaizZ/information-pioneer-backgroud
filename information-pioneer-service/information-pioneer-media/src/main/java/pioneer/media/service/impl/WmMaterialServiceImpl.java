package pioneer.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import pioneer.common.dto.PageResponseResult;
import pioneer.common.dto.ResponseResult;
import pioneer.common.dto.User;
import pioneer.common.enums.AppHttpCodeEnum;
import pioneer.common.minio.MinIOService;
import pioneer.common.util.UserThreadLocalUtil;
import pioneer.media.dto.WmMaterialDto;
import pioneer.media.entity.WmMaterial;
import pioneer.media.entity.WmNewsMaterial;
import pioneer.media.mapper.WmMaterialMapper;
import pioneer.media.service.IWmMaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pioneer.media.service.IWmNewsMaterialService;

import java.util.Date;

/**
 * 自媒体图文素材信息表 服务实现类
 */
@Service
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements IWmMaterialService {
    @Autowired
    private MinIOService minIOService;
    @Autowired
    private IWmNewsMaterialService wmNewsMaterialService;
    @Override
    public ResponseResult<WmMaterial> saveWmMaterial(MultipartFile file) {
        //上传图片
        String url = minIOService.upload(file);

        User user = UserThreadLocalUtil.get();
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        WmMaterial wmMaterial = new WmMaterial();
        wmMaterial.setUserId(user.getUserId());
        wmMaterial.setUrl(url);
        wmMaterial.setType(0);
        wmMaterial.setIsCollection(false);
        wmMaterial.setCreatedTime(new Date());

        this.save(wmMaterial);
        return ResponseResult.okResult(wmMaterial);
    }

    @Override
    public ResponseResult listByStatus(WmMaterialDto dto) {
        if (dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        User user = UserThreadLocalUtil.get();
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        LambdaQueryWrapper<WmMaterial> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(dto.getIsCollection()!=null,WmMaterial::getIsCollection, dto.getIsCollection());
        queryWrapper.eq(WmMaterial::getUserId,user.getUserId());
        queryWrapper.orderByDesc(WmMaterial::getCreatedTime);

        Page<WmMaterial> page = new Page<>(dto.getPage(), dto.getSize());
        Page<WmMaterial> result = this.page(page, queryWrapper);

        return new PageResponseResult(dto.getPage(), dto.getSize(), result.getTotal(),result.getRecords());
    }

    @Override
    public ResponseResult collection(Integer id, int type) {
        WmMaterial wmMaterial = this.getById(id);

        User user = UserThreadLocalUtil.get();
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        if (wmMaterial == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //收藏文章
        if (type == 1){
            wmMaterial.setIsCollection(true);
        }else {
            wmMaterial.setIsCollection(false);
        }

        this.updateById(wmMaterial);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMaterial(Integer id) {
        User user = UserThreadLocalUtil.get();
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        LambdaQueryWrapper<WmNewsMaterial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WmNewsMaterial::getMaterialId,id);

        //删除文章的引用
        wmNewsMaterialService.remove(queryWrapper);

        this.removeById(id);

        return ResponseResult.okResult();
    }
}
