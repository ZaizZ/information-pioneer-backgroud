package pioneer.comment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pioneer.comment.entity.ApUser;
import pioneer.common.dto.ResponseResult;

@FeignClient("information-pioneer-user")
public interface UserFeign {

    @GetMapping("/api/v1/user/{id}")
    public ResponseResult<ApUser> getById(@PathVariable("id") Integer id);

    }
