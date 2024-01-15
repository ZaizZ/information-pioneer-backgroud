package pioneer.behavior.dto;

import lombok.Data;

@Data
public class BehaviorDto {
    /**
     * 文章作者id
     */
    private Integer authorId;
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 设备id
     */
    private String equipmentId;
    /**
     * 操作类型 0  关注 点赞 不喜欢 收藏    1 取消关注 取消点赞 取消不喜欢 取消收藏
     */
    private Integer operation = 0;

//    当前登录人的ID
    private Integer userId;

    /**
     * 阅读次数
     */
    Integer count;
    /**
     * 阅读时长（S)
     */
    Integer readDuration;
    /**
     * 阅读百分比
     */
    Integer percentage;
    /**
     * 加载时间
     */
    Integer loadDuration;

}
