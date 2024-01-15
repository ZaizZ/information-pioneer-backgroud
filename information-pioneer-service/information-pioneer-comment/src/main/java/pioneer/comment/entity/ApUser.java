package pioneer.comment.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * APP用户信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description="APP用户信息表")
public class ApUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 密码、通信等加密盐
     */
    @ApiModelProperty(value = "密码、通信等加密盐")
    private String salt;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String name;

    /**
     * 密码,md5加密
     */
    @ApiModelProperty(value = "密码,md5加密")
    private String password;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String image;

    /**
     * 0 男
            1 女
            2 未知
     */
    @ApiModelProperty(value = "0 男            1 女            2 未知")
    private Integer sex;

    /**
     * 0 未
            1 是
     */
    @ApiModelProperty(value = "0 未            1 是")
    private Boolean isCertification;

    /**
     * 是否身份认证
     */
    @ApiModelProperty(value = "是否身份认证")
    private Boolean isIdentityAuthentication;

    /**
     * 0正常
            1锁定
     */
    @ApiModelProperty(value = "0正常            1锁定")
    private Integer status;

    /**
     * 0 普通用户
            1 自媒体人
            2 大V
     */
    @ApiModelProperty(value = "0 普通用户            1 自媒体人            2 大V")
    private Integer flag;

    /**
     * 注册时间
     */
    @ApiModelProperty(value = "注册时间")
    private Date createdTime;


}
