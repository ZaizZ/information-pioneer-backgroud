package pioneer.media.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pioneer.media.entity.WmNews;

@Data
@EqualsAndHashCode(callSuper = true)
public class WmNewsVo  extends WmNews {
    /**
     * 作者名称
     */
    private String authorName;
}
