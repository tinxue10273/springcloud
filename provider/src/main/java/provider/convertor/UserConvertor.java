package provider.convertor;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.util.ObjectUtils;
import provider.common.JsonTool;
import provider.domain.UserDO;
import provider.vo.UserVO;

import java.util.Set;

public class UserConvertor {
    public static UserVO convertorVO(UserDO user) {
        if(ObjectUtils.isEmpty(user)){
            return null;
        }
        UserVO userVO = UserVO.builder().id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .salt(user.getSalt())
                .email(user.getEmail())
                .type(user.getType())
                .activationCode(user.getActivationCode())
                .headerUrl(user.getHeaderUrl())
                .createTime(user.getCreateTime())
                .build();
        String tagStr = user.getTag();
        if(!ObjectUtils.isEmpty(tagStr)){
            Set<String> tags  = JsonTool.fromJson(tagStr, new TypeReference<Set<String>>() {
            });
            userVO.setTags(tags);
        }
        return userVO;
    }
}
