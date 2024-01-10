package pioneer.media.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
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
 * 敏感词信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wm_sensitive")
@ApiModel(description="敏感词信息表")
public class WmSensitive implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelProperty("序号")
    private Integer id;

    /**
     * 敏感词
     */
    @ApiModelProperty(value = "敏感词")
    @TableField("sensitives")
    @ExcelProperty("敏感词")
    private String sensitives;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    @ExcelProperty("创建时间")
    @ExcelIgnore //导出的时候忽略
    private Date createdTime;


}
