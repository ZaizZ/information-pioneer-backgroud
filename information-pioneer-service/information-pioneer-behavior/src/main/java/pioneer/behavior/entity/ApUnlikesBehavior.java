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
 * APP不喜欢行为表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Document("ap_unlikes_behavior")
@ApiModel(description="APP不喜欢行为表")
public class ApUnlikesBehavior implements Serializable {

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
     * 0 不喜欢
            1 取消不喜欢
     */
    @ApiModelProperty(value = "0 不喜欢            1 取消不喜欢")
    private Integer operation;

    /**
     * 登录时间
     */
    @ApiModelProperty(value = "登录时间")
    private Date createdTime;


}
