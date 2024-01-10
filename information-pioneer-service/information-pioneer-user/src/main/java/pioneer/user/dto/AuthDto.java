package pioneer.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pioneer.common.dto.PageRequestDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthDto extends PageRequestDto {
    /**
     * 状态
     */
    private Integer status;

    private Integer id;
    private String msg;
}
