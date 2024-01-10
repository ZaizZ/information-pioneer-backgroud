package pioneer.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * APP文章作者信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description="APP文章作者信息表")
public class ApAuthor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 作者名称
     */
    @ApiModelProperty(value = "作者名称")
    private String name;

    /**
     * 0 爬取数据
            1 签约合作商
            2 平台自媒体人

     */
    @ApiModelProperty(value = "0 爬取数据            1 签约合作商            2 平台自媒体人            ")
    private Integer type;

    /**
     * 社交账号ID
     */
    @ApiModelProperty(value = "社交账号ID")
    private Integer userId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 自媒体账号
     */
    @ApiModelProperty(value = "自媒体账号")
    private Integer wmUserId;


}
