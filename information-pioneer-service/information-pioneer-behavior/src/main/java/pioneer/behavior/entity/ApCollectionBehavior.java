package pioneer.behavior.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * APP收藏信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Document("ap_collection_behavior")
@ApiModel(description="APP收藏信息表")
public class ApCollectionBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 实体ID
     */
    @ApiModelProperty(value = "实体ID")
    private Integer entryId;

    /**
     * 0 收藏
            1 取消收藏
     */
    @ApiModelProperty(value = "0 收藏            1 取消收藏")
    private Integer operation;

    /**
     * 文章ID
     */
    @ApiModelProperty(value = "文章ID")
    private Long articleId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date collectionTime;


}
