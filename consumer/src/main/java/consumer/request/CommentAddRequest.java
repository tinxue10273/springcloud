package consumer.request;

import consumer.vo.CommentVO;
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
public class CommentAddRequest extends BaseRequest {
    private Long discussId;
    private CommentVO comment;
}
