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

/**
 * APP行为实体表,一个行为实体可能是用户或者设备，或者其它
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_behavior_entry")
@ApiModel(description="APP行为实体表,一个行为实体可能是用户或者设备，或者其它")
public class ApBehaviorEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 实体类型
            0终端设备
            1用户
     */
    @ApiModelProperty(value = "实体类型            0终端设备            1用户")
    @TableField("type")
    private Integer type;

    /**
     * 实体ID
     */
    @ApiModelProperty(value = "实体ID")
    @TableField("entry_id")
    private String entryId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime;


}
