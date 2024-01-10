package pioneer.media.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import pioneer.common.dto.PageRequestDto;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class WmNewsPageDto extends PageRequestDto {

    private Integer status;//状态
    private Date beginPubDate;//开始时间
    private Date endPubDate;//结束时间
    private Integer channelId;//所属频道ID
    private String keyword;//关键字
}
