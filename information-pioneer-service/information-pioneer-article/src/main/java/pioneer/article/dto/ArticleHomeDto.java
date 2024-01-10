package pioneer.article.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ArticleHomeDto{

    // 最大时间
    Date maxTime;
    // 最小时间
    Date minTime;
    // 分页size
    Integer size;
    // 频道ID
    Integer channelId; //值为0代表所有频道
}
