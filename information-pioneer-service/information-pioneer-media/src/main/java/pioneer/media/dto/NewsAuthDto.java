package pioneer.media.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pioneer.common.dto.PageRequestDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewsAuthDto extends PageRequestDto {
    /**
     * 标题
     */
    private String title;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 文章id
     */
    private Integer id;
    /**
     * 失败原因
     */
    private String msg;
}
