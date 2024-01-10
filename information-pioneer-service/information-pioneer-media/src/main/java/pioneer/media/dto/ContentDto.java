package pioneer.media.dto;

import lombok.Data;

@Data
public class ContentDto {
    /**
     * 类型  text:文本  image:图片
     */
    private String type;
    /**
     * 值
     */
    private String value;
    /**
     * 如果有图片,传的是图片对应的素材ID
     */
    private Integer id;
}
