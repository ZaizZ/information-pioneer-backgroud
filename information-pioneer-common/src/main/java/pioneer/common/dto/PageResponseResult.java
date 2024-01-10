package pioneer.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用分页结果
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PageResponseResult<T> extends ResponseResult<T> implements Serializable {
    /**
     * 当前页
     */
    private Long currentPage;
    /**
     * 每页条数
     */
    private Integer size;
    /**
     * 总数
     */
    private Long total;

    public PageResponseResult(Long currentPage, Integer size, Long total) {
        this.currentPage = currentPage;
        this.size = size;
        this.total = total;
    }

    public PageResponseResult(Long currentPage, Integer size, Long total, T data) {
        this.currentPage = currentPage;
        this.size = size;
        this.total = total;
        this.setData(data);
    }
}
