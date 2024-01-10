package pioneer.common.dto;

import lombok.Setter;

/**
 * 公共分页请求对象
 */
@Setter
public class PageRequestDto {
    /**
     * 每页条数
     */
    protected Integer size;
    /**
     * 当前页
     */
    protected Long page;

    public Integer getSize() {
        if (this.size == null || this.size <= 0 || this.size > 100) {
            setSize(10);
        }
        return size;
    }

    public Long getPage() {
        if (this.page == null || this.page <= 0) {
            setPage(1L);
        }
        return page;
    }
}
