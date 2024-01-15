package pioneer.comment.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pioneer.comment.entity.ApArticle;
import pioneer.common.dto.ResponseResult;

@FeignClient("information-pioneer-article")
public interface ArticleFeign {

    @GetMapping("/api/v1/article/load_by_id/{id}")
    public ResponseResult<ApArticle> loadById(@PathVariable("id") Long articleId);
}
