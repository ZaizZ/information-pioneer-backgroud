package pioneer.search.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pioneer.common.dto.PageRequestDto;
import pioneer.search.entity.ApUserSearch;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApArticleSearchDto extends PageRequestDto {

    // 设备ID
    String equipmentId;
    // 关键字
    String searchWords;
    // id
    List<ApUserSearch> hisList;

}
