package pioneer.media.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public class ArticleDto{
    /**
     * 文章内容
     */
    private String content;

    private Long id; //如果保存时如果为null则会使用雪花算法
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
    private String images; //注意

    /**
     * 文章标签最多3个 逗号分隔
     */
    @ApiModelProperty(value = "文章标签最多3个 逗号分隔")
    private String labels;


    @ApiModelProperty(value = "发布时间")
    private Date publishTime;
}
