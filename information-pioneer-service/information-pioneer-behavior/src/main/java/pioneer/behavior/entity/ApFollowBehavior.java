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
 * APP关注行为表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Document("ap_follow_behavior")
@ApiModel(description="APP关注行为表")
public class ApFollowBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 实体ID
     */
    @ApiModelProperty(value = "实体ID")
    private Integer entryId;

    /**
     * 关注用户ID
     */
    @ApiModelProperty(value = "关注用户ID")
    private Integer followId;

    /**
     * 操作类型: 0 关注 1 取消关注
     */
    @ApiModelProperty(value = "操作类型: 0 关注 1 取消关注")
    private Integer operation;

    /**
     * 登录时间
     */
    @ApiModelProperty(value = "登录时间")
    private Date createdTime;


}
