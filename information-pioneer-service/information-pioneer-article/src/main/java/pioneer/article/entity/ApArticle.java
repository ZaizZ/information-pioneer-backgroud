package pioneer.article.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章信息表，存储已发布的文章
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_article")
@ApiModel(description="文章信息表，存储已发布的文章")
public class ApArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class) //返回给前端字符串型的id，解决精度丢失的问题
    private Long id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    /**
     * 文章作者的ID
     */
    @ApiModelProperty(value = "文章作者的ID")
    @TableField("author_id")
    private Integer authorId;

    /**
     * 作者昵称
     */
    @ApiModelProperty(value = "作者昵称")
    @TableField("author_name")
    private String authorName;

    /**
     * 文章所属频道ID
     */
    @ApiModelProperty(value = "文章所属频道ID")
    @TableField("channel_id")
    private Integer channelId;

    /**
     * 频道名称
     */
    @ApiModelProperty(value = "频道名称")
    @TableField("channel_name")
    private String channelName;

    /**
     * 文章布局
            0 无图文章
            1 单图文章
            2 多图文章
     */
    @ApiModelProperty(value = "文章布局            0 无图文章            1 单图文章            2 多图文章")
    @TableField("layout")
    private Integer layout;

    /**
     * 文章标记
            0 普通文章
            1 热点文章
            2 置顶文章
            3 精品文章
            4 大V 文章
     */
    @ApiModelProperty(value = "文章标记            0 普通文章            1 热点文章            2 置顶文章            3 精品文章            4 大V 文章")
    @TableField("flag")
    private Integer flag;

    /**
     * 文章图片
            多张逗号分隔
     */
    @ApiModelProperty(value = "文章图片            多张逗号分隔")
    @TableField("images")
    private String images;

    /**
     * 文章标签最多3个 逗号分隔
     */
    @ApiModelProperty(value = "文章标签最多3个 逗号分隔")
    @TableField("labels")
    private String labels;

    /**
     * 点赞数量
     */
    @ApiModelProperty(value = "点赞数量")
    @TableField("likes")
    private Integer likes;

    /**
     * 收藏数量
     */
    @ApiModelProperty(value = "收藏数量")
    @TableField("collection")
    private Integer collection;

    /**
     * 评论数量
     */
    @ApiModelProperty(value = "评论数量")
    @TableField("comment")
    private Integer comment;

    /**
     * 阅读数量
     */
    @ApiModelProperty(value = "阅读数量")
    @TableField("views")
    private Integer views;

    /**
     * 省市
     */
    @ApiModelProperty(value = "省市")
    @TableField("province_id")
    private Integer provinceId;

    /**
     * 市区
     */
    @ApiModelProperty(value = "市区")
    @TableField("city_id")
    private Integer cityId;

    /**
     * 区县
     */
    @ApiModelProperty(value = "区县")
    @TableField("county_id")
    private Integer countyId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    @TableField("publish_time")
    private Date publishTime;

    /**
     * 同步状态
     */
    @ApiModelProperty(value = "同步状态")
    @TableField("sync_status")
    private Integer syncStatus;

    /**
     * 来源
     */
    @ApiModelProperty(value = "来源")
    @TableField("origin")
    private Integer origin;

    /**
     * 是否可评论
     */
    @ApiModelProperty(value = "是否可评论")
    @TableField("is_comment")
    private Boolean isComment;

    /**
     * 是否可转发
     */
    @ApiModelProperty(value = "是否可转发")
    @TableField("is_forward")
    private Boolean isForward;

    /**
     * 是否下架
     */
    @ApiModelProperty(value = "是否下架")
    @TableField("is_down")
    private Boolean isDown;

    /**
     * 是否已删除
     */
    @ApiModelProperty(value = "是否已删除")
    @TableField("is_delete")
    private Boolean isDelete;

    /**
     * 静态页面地址
     */
    @ApiModelProperty(value = "静态页面地址")
    @TableField("static_url")
    private String staticUrl;


}
