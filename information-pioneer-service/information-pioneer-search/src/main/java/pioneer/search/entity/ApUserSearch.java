package pioneer.search.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * APP用户搜索信息表
 */
@Data
@Document("ap_user_search")
public class ApUserSearch implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 设备id
     */
    private String equipmentId;

    /**
     * 搜索词
     */
    private String keyword;

    /**
     * 创建时间
     */
    private Date createdTime;

}
