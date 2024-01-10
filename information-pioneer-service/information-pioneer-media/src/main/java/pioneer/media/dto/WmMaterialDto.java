package pioneer.media.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import pioneer.common.dto.PageRequestDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class WmMaterialDto extends PageRequestDto {
    /**
     * 收藏状态
     */
    private Integer isCollection; //1 查询收藏的
}
