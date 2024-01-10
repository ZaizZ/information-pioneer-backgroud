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
 * 自媒体图文内容信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wm_news")
@ApiModel(description="自媒体图文内容信息表")
public class WmNews implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 自媒体用户ID
     */
    @ApiModelProperty(value = "自媒体用户ID")
    @TableField("user_id")
    private Integer userId;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    /**
     * 图文内容
     */
    @ApiModelProperty(value = "图文内容")
    @TableField("content")
    private String content;

    /**
     * 文章布局
            0 无图文章
            1 单图文章
            3 多图文章
     */
    @ApiModelProperty(value = "文章布局            0 无图文章            1 单图文章            3 多图文章")
    @TableField("type")
    private Integer type;

    /**
     * 图文频道ID
     */
    @ApiModelProperty(value = "图文频道ID")
    @TableField("channel_id")
    private Integer channelId;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    @TableField("labels")
    private String labels;

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
     * 当前状态
            0 草稿
            1 提交（待审核）
            2 审核失败
            3 人工审核
            4 人工审核通过
            8 审核通过（待发布）
            9 已发布
     */
    @ApiModelProperty(value = "当前状态            0 草稿            1 提交（待审核）            2 审核失败            3 人工审核            4 人工审核通过            8 审核通过（待发布）            9 已发布")
    @TableField("status")
    private Integer status;

    /**
     * 定时发布时间，不定时则为空
     */
    @ApiModelProperty(value = "定时发布时间，不定时则为空")
    @TableField("publish_time")
    private Date publishTime;

    /**
     * 拒绝理由
     */
    @ApiModelProperty(value = "拒绝理由")
    @TableField("reason")
    private String reason;

    /**
     * 发布库文章ID
     */
    @ApiModelProperty(value = "发布库文章ID")
    @TableField("article_id")
    private Long articleId;

    /**
     * 封面图片列表 多张图以逗号隔开
     */
    @ApiModelProperty(value = "封面图片列表 多张图以逗号隔开")
    @TableField("images")
    private String images;

    /**
     * 上架状态 1 上架 0 下架
     */
    @ApiModelProperty(value = "上架状态 1 上架 0 下架")
    @TableField("enable")
    private Boolean enable;


}
