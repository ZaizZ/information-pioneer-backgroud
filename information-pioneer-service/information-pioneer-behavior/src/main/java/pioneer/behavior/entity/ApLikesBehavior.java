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
 * APP点赞行为表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Document("ap_likes_behavior")
@ApiModel(description="APP点赞行为表")
public class ApLikesBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 实体ID
     */
    @ApiModelProperty(value = "实体ID")
    private Integer entryId;

    /**
     * 文章ID
     */
    @ApiModelProperty(value = "文章ID")
    private Long articleId;

    /**
     * 点赞内容类型
            0文章
            1动态
     */
    @ApiModelProperty(value = "点赞内容类型            0文章            1动态")
    private Integer type;

    /**
     * 0 点赞
            1 取消点赞
     */
    @ApiModelProperty(value = "0 点赞            1 取消点赞")
    private Integer operation;

    /**
     * 登录时间
     */
    @ApiModelProperty(value = "登录时间")
    private Date createdTime;


}
