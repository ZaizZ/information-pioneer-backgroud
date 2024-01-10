package pioneer.media.entity;

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
 * 自媒体图文素材信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wm_material")
@ApiModel(description="自媒体图文素材信息表")
public class WmMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 自媒体用户ID
     */
    @ApiModelProperty(value = "自媒体用户ID")
    @TableField("user_id")
    private Integer userId;

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    @TableField("url")
    private String url;

    /**
     * 素材类型
            0 图片
            1 视频
     */
    @ApiModelProperty(value = "素材类型            0 图片            1 视频")
    @TableField("type")
    private Integer type;

    /**
     * 是否收藏
     */
    @ApiModelProperty(value = "是否收藏")
    @TableField("is_collection")
    private Boolean isCollection;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime;


}
