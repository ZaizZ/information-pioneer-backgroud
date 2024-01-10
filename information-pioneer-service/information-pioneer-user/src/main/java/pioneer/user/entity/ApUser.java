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
 * APP用户信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user")
@ApiModel(description="APP用户信息表")
public class ApUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 密码、通信等加密盐
     */
    @ApiModelProperty(value = "密码、通信等加密盐")
    @TableField("salt")
    private String salt;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @TableField("name")
    private String name;

    /**
     * 密码,md5加密
     */
    @ApiModelProperty(value = "密码,md5加密")
    @TableField("password")
    private String password;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    @TableField("image")
    private String image;

    /**
     * 0 男
            1 女
            2 未知
     */
    @ApiModelProperty(value = "0 男            1 女            2 未知")
    @TableField("sex")
    private Integer sex;

    /**
     * 0 未
            1 是
     */
    @ApiModelProperty(value = "0 未            1 是")
    @TableField("is_certification")
    private Boolean isCertification;

    /**
     * 是否身份认证
     */
    @ApiModelProperty(value = "是否身份认证")
    @TableField("is_identity_authentication")
    private Boolean isIdentityAuthentication;

    /**
     * 0正常
            1锁定
     */
    @ApiModelProperty(value = "0正常            1锁定")
    @TableField("status")
    private Integer status;

    /**
     * 0 普通用户
            1 自媒体人
            2 大V
     */
    @ApiModelProperty(value = "0 普通用户            1 自媒体人            2 大V")
    @TableField("flag")
    private Integer flag;

    /**
     * 注册时间
     */
    @ApiModelProperty(value = "注册时间")
    @TableField("created_time")
    private Date createdTime;


}
