package pioneer.article.entity;

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
 * APP文章作者信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_author")
@ApiModel(description="APP文章作者信息表")
public class ApAuthor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 作者名称
     */
    @ApiModelProperty(value = "作者名称")
    @TableField("name")
    private String name;

    /**
     * 0 爬取数据
            1 签约合作商
            2 平台自媒体人
            
     */
    @ApiModelProperty(value = "0 爬取数据            1 签约合作商            2 平台自媒体人            ")
    @TableField("type")
    private Integer type;

    /**
     * 社交账号ID
     */
    @ApiModelProperty(value = "社交账号ID")
    @TableField("user_id")
    private Integer userId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime;

    /**
     * 自媒体账号
     */
    @ApiModelProperty(value = "自媒体账号")
    @TableField("wm_user_id")
    private Integer wmUserId;


}
