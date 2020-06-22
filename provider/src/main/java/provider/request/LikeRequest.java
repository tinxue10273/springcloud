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
public class LikeRequest  extends BaseRequest{
    private Byte entityType;
    private Integer entityId;
    private Integer entityUserId;
    private Integer postId;
}
