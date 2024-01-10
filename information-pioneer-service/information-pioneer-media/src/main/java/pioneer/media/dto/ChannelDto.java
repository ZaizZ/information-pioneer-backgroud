package pioneer.media.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pioneer.common.dto.PageRequestDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelDto extends PageRequestDto {

    /**
     * 频道名称
     */
    @ApiModelProperty(value = "频道名称")
    private String name;
}
