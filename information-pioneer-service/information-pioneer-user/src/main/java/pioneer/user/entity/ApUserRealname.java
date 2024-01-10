package pioneer.user.entity;

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
 * APP实名认证信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user_realname")
@ApiModel(description="APP实名认证信息表")
public class ApUserRealname implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号ID
     */
    @ApiModelProperty(value = "账号ID")
    @TableField("user_id")
    private Integer userId;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    @TableField("name")
    private String name;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    @TableField("idno")
    private String idno;

    /**
     * 正面照片
     */
    @ApiModelProperty(value = "正面照片")
    @TableField("font_image")
    private String fontImage;

    /**
     * 背面照片
     */
    @ApiModelProperty(value = "背面照片")
    @TableField("back_image")
    private String backImage;

    /**
     * 手持照片
     */
    @ApiModelProperty(value = "手持照片")
    @TableField("hold_image")
    private String holdImage;

    /**
     * 活体照片
     */
    @ApiModelProperty(value = "活体照片")
    @TableField("live_image")
    private String liveImage;

    /**
     * 状态
            0 创建中
            1 待审核
            2 审核失败
            9 审核通过
     */
    @ApiModelProperty(value = "状态            0 创建中            1 待审核            2 审核失败            9 审核通过")
    @TableField("status")
    private Integer status;

    /**
     * 拒绝原因
     */
    @ApiModelProperty(value = "拒绝原因")
    @TableField("reason")
    private String reason;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime;

    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
    @TableField("submited_time")
    private Date submitedTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField("updated_time")
    private Date updatedTime;


}
