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
 * APP阅读行为表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Document("ap_read_behavior")
@ApiModel(description="APP阅读行为表")
public class ApReadBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Integer entryId;

    /**
     * 文章ID
     */
    @ApiModelProperty(value = "文章ID")
    private Long articleId;

    /**
     * 阅读次数
     */
    @ApiModelProperty(value = "阅读次数")
    private Integer count;

    /**
     * 阅读时间单位秒
     */
    @ApiModelProperty(value = "阅读时间单位秒")
    private Integer readDuration;

    /**
     * 阅读百分比
     */
    @ApiModelProperty(value = "阅读百分比")
    private Integer percentage;

    /**
     * 文章加载时间
     */
    @ApiModelProperty(value = "文章加载时间")
    private Integer loadDuration;

    /**
     * 登录时间
     */
    @ApiModelProperty(value = "登录时间")
    private Date createdTime;

    private Date updatedTime;


}
