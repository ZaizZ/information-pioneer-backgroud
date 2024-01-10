package pioneer.article.service;

import pioneer.article.entity.ApAuthor;
import com.baomidou.mybatisplus.extension.service.IService;
import pioneer.common.dto.ResponseResult;

/**
 * APP文章作者信息表 服务类
 */
public interface IApAuthorService extends IService<ApAuthor> {

    ResponseResult<ApAuthor> saveApAuthor(ApAuthor apAuthor);
}
