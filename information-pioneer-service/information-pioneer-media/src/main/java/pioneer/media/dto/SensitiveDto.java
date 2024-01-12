package pioneer.media.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pioneer.common.dto.PageRequestDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class SensitiveDto extends PageRequestDto {
    /**
     * 敏感词
     */
    private String name;

}
