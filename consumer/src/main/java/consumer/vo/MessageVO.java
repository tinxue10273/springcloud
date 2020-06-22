package consumer.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @authorgouhuo on 2020/04/27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageVO implements Serializable {
    private int id;
    private int fromId;
    private int toId;
    private String conversationId;
    private String content;
    private Date createTime;
}
