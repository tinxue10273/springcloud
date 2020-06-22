package provider.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @authorgouhuo on 2020/05/18.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileVO implements Serializable {
    private UserVO user;
    private Integer likeCount;
    private Long followeeCount;
    private Long followerCount;
    private Boolean hasFollowed;
}
