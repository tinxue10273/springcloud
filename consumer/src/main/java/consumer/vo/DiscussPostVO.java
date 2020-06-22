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
public class DiscussPostVO implements Serializable {
    private int id;

    private int userId;

    private String title;

    private String content;

    private int type;

    private int status;

    private Date createTime;

    private int commentCount;
    private double score;
}
