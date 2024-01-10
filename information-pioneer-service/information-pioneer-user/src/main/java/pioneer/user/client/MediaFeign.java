package pioneer.user.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pioneer.common.dto.ResponseResult;
import pioneer.user.dto.WmUser;

@FeignClient("information-pioneer-media")
public interface MediaFeign {
    @PostMapping("/api/v1/user")
    public ResponseResult<WmUser> saveWmUser(@RequestBody WmUser wmUser);

    @PostMapping("/api/v1/user")
    public ResponseResult updateWmUser(@RequestBody WmUser wmUser);
}
