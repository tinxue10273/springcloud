package provider.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @authorgouhuo on 2020/04/27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowRequest extends BaseRequest {
    private Integer entityType;
    private Integer entityId;
    private Integer current;
    private Integer pageSize;
}
