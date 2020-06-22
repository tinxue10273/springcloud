package consumer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author guohuo on 2020/04/10.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseTableVO<T> implements Serializable {
    private List<T> items;
    private Pagination pagination;


    public void initPagination(Integer current, Integer pageSize, Integer total) {
        if ((null == pagination)) {
            pagination = new Pagination();
        }
        pagination.setCurrent(current);
        pagination.setPageSize(pageSize);
        pagination.setTotal(total);
    }
}
