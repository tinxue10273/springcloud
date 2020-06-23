package provider.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @authorgouhuo on 2020/04/27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO implements Serializable {
    private int id;
    private Set<String> tags;
    private String username;
    private String password;
    private String salt;
    private String email;
    private int type;
    private String activationCode;
    private String headerUrl;
    private Date createTime;
}
