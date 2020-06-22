package provider.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @authorgouhuo on 2020/05/18.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchVO implements Serializable {
    private List<Map<String, Object>> discussPosts;
    private String keyWord;
    private Integer current;
    private Integer pageSize;
    private Integer total;
}
