package pioneer.media.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自媒体图文引用素材信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wm_news_material")
@ApiModel(description="自媒体图文引用素材信息表")
public class WmNewsMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 素材ID
     */
    @ApiModelProperty(value = "素材ID")
    @TableField("material_id")
    private Integer materialId;

    /**
     * 图文ID
     */
    @ApiModelProperty(value = "图文ID")
    @TableField("news_id")
    private Integer newsId;

    /**
     * 引用类型
            0 内容引用
            1 主图引用
     */
    @ApiModelProperty(value = "引用类型            0 内容引用            1 主图引用")
    @TableField("type")
    private Integer type;

    /**
     * 引用排序
     */
    @ApiModelProperty(value = "引用排序")
    @TableField("ord")
    private Integer ord;


}
