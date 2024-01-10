package pioneer.admin.entity;

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
 * 管理员用户信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ad_user")
@ApiModel(description="管理员用户信息表")
public class AdUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登录用户名
     */
    @ApiModelProperty(value = "登录用户名")
    @TableField("name")
    private String name;

    /**
     * 登录密码
     */
    @ApiModelProperty(value = "登录密码")
    @TableField("password")
    private String password;

    /**
     * 盐
     */
    @ApiModelProperty(value = "盐")
    @TableField("salt")
    private String salt;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    @TableField("nickname")
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    @TableField("image")
    private String image;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    /**
     * 状态
            0 暂时不可用
            1 永久不可用
            9 正常可用
     */
    @ApiModelProperty(value = "状态            0 暂时不可用            1 永久不可用            9 正常可用")
    @TableField("status")
    private Integer status;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    /**
     * 最后一次登录时间
     */
    @ApiModelProperty(value = "最后一次登录时间")
    @TableField("login_time")
    private Date loginTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime;


}
