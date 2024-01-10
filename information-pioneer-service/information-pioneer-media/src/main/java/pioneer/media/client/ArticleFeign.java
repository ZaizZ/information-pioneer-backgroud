package pioneer.media.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pioneer.common.dto.ResponseResult;
import pioneer.media.dto.ArticleDto;

@FeignClient("information-pioneer-article")
public interface ArticleFeign {

    @PostMapping("/api/v1/article")
    public ResponseResult<Long> saveArticle(@RequestBody ArticleDto dto);
}
