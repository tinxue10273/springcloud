package consumer.request;

import consumer.vo.DiscussPostVO;
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
public class DiscussRequest extends BaseRequest {
    private DiscussPostVO discussPostVO;
}
