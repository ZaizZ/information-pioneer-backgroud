package pioneer.user.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pioneer.common.dto.ResponseResult;
import pioneer.user.dto.ApAuthor;

@FeignClient("information-pioneer-article")
public interface ArticleFeign {
    @PostMapping("/api/v1/author")
    public ResponseResult<ApAuthor> saveApAuthor(@RequestBody ApAuthor apAuthor);
}
