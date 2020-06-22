package provider.request;

import provider.vo.UserVO;
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
public class RegisterRequest extends BaseRequest{
    private UserVO user;
}
