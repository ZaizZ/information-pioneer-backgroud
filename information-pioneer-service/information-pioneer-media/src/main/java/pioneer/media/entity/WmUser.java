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
 * 自媒体用户信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wm_user")
@ApiModel(description="自媒体用户信息表")
public class WmUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("ap_user_id")
    private Integer apUserId;

    @TableField("ap_author_id")
    private Integer apAuthorId;

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
     * 归属地
     */
    @ApiModelProperty(value = "归属地")
    @TableField("location")
    private String location;

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
     * 账号类型
            0 个人
            1 企业
            2 子账号
     */
    @ApiModelProperty(value = "账号类型            0 个人             1 企业            2 子账号")
    @TableField("type")
    private Integer type;

    /**
     * 运营评分
     */
    @ApiModelProperty(value = "运营评分")
    @TableField("score")
    private Integer score;

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
