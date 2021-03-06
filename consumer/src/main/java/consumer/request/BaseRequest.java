package consumer.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseRequest implements Serializable {
    private String cookie;
    private String ticket;
    private Long id;
    private String other;
}
