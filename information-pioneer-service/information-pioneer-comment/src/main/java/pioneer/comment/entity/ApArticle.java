package pioneer.comment.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章信息表，存储已发布的文章
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description="文章信息表，存储已发布的文章")
public class ApArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class) //返回给前端字符串型的id，解决精度丢失的问题
    private Long id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 文章作者的ID
     */
    @ApiModelProperty(value = "文章作者的ID")
    private Integer authorId;

    /**
     * 作者昵称
     */
    @ApiModelProperty(value = "作者昵称")
    private String authorName;

    /**
     * 文章所属频道ID
     */
    @ApiModelProperty(value = "文章所属频道ID")
    private Integer channelId;

    /**
     * 频道名称
     */
    @ApiModelProperty(value = "频道名称")
    private String channelName;

    /**
     * 文章布局
            0 无图文章
            1 单图文章
            2 多图文章
     */
    @ApiModelProperty(value = "文章布局            0 无图文章            1 单图文章            2 多图文章")
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
    private Integer flag;

    /**
     * 文章图片
            多张逗号分隔
     */
    @ApiModelProperty(value = "文章图片            多张逗号分隔")
    private String images;

    /**
     * 文章标签最多3个 逗号分隔
     */
    @ApiModelProperty(value = "文章标签最多3个 逗号分隔")
    private String labels;

    /**
     * 点赞数量
     */
    @ApiModelProperty(value = "点赞数量")
    private Integer likes;

    /**
     * 收藏数量
     */
    @ApiModelProperty(value = "收藏数量")
    private Integer collection;

    /**
     * 评论数量
     */
    @ApiModelProperty(value = "评论数量")
    private Integer comment;

    /**
     * 阅读数量
     */
    @ApiModelProperty(value = "阅读数量")
    private Integer views;

    /**
     * 省市
     */
    @ApiModelProperty(value = "省市")
    private Integer provinceId;

    /**
     * 市区
     */
    @ApiModelProperty(value = "市区")
    private Integer cityId;

    /**
     * 区县
     */
    @ApiModelProperty(value = "区县")
    private Integer countyId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    private Date publishTime;

    /**
     * 同步状态
     */
    @ApiModelProperty(value = "同步状态")
    private Integer syncStatus;

    /**
     * 来源
     */
    @ApiModelProperty(value = "来源")
    private Integer origin;

    /**
     * 是否可评论
     */
    @ApiModelProperty(value = "是否可评论")
    private Boolean isComment;

    /**
     * 是否可转发
     */
    @ApiModelProperty(value = "是否可转发")
    private Boolean isForward;

    /**
     * 是否下架
     */
    @ApiModelProperty(value = "是否下架")
    private Boolean isDown;

    /**
     * 是否已删除
     */
    @ApiModelProperty(value = "是否已删除")
    private Boolean isDelete;

    /**
     * 静态页面地址
     */
    @ApiModelProperty(value = "静态页面地址")
    private String staticUrl;


}
