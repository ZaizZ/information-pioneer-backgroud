package pioneer.user.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 自媒体用户信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description="自媒体用户信息表")
public class WmUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    private Integer apUserId;

    private Integer apAuthorId;

    /**
     * 登录用户名
     */
    @ApiModelProperty(value = "登录用户名")
    private String name;

    /**
     * 登录密码
     */
    @ApiModelProperty(value = "登录密码")
    private String password;

    /**
     * 盐
     */
    @ApiModelProperty(value = "盐")
    private String salt;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String image;

    /**
     * 归属地
     */
    @ApiModelProperty(value = "归属地")
    private String location;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 状态
            0 暂时不可用
            1 永久不可用
            9 正常可用
     */
    @ApiModelProperty(value = "状态            0 暂时不可用            1 永久不可用            9 正常可用")
    private Integer status;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 账号类型
            0 个人
            1 企业
            2 子账号
     */
    @ApiModelProperty(value = "账号类型            0 个人             1 企业            2 子账号")
    private Integer type;

    /**
     * 运营评分
     */
    @ApiModelProperty(value = "运营评分")
    private Integer score;

    /**
     * 最后一次登录时间
     */
    @ApiModelProperty(value = "最后一次登录时间")
    private Date loginTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;


}
