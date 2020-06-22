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
public class MessageRequest extends BaseRequest {
    private String topic;
    private String toName;
    private String content;
    private String conversationId;
    private Integer current;
    private Integer pageSize;

}
