package provider.convertor;

import org.springframework.util.ObjectUtils;
import provider.domain.UserDO;
import provider.vo.UserVO;

public class UserConvertor {
    public static UserVO convertorVO(UserDO user) {
        if(ObjectUtils.isEmpty(user)){
            return null;
        }
        return UserVO.builder().id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .salt(user.getSalt())
                .email(user.getEmail())
                .type(user.getType())
                .activationCode(user.getActivationCode())
                .headerUrl(user.getHeaderUrl())
                .createTime(user.getCreateTime())
                .build();
    }
}
