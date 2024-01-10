package pioneer.media.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WmNewsDto {

    /**
     * id
     */
    private Integer id;
     /**
     * 标题
     */
    private String title;
     /**
     * 频道id
     */
    private Integer channelId;
     /**
     * 标签
     */
    private String labels;
     /**
     * 发布时间
     */
    private Date publishTime;
     /**
     * 文章内容
     */
    private String content;
     /**
     * 文章封面类型  0 无图 1 单图 3 多图 -1 自动
     */
    private Integer type;
     /**
     * 是否上架  0 下架  1 上架
     */
    private Integer enable;
     /**
     * 提交时间
     */
    private Date submitedTime;
     /**
     * 状态 提交为1  草稿为0
     */
    private Integer status;
     /**
     * 拒绝理由
     */
    private String reason;
     /**
     * 封面图片列表 多张图以逗号隔开
     */
    private List<ImageDto> images;
}
