package provider.service;

import com.fasterxml.jackson.core.type.TypeReference;
import io.micrometer.core.instrument.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import provider.common.JsonTool;
import provider.convertor.UserConvertor;
import provider.domain.UserDO;
import provider.repository.impl.UserDORepositoryImpl;
import provider.vo.TicketVO;
import provider.vo.UserVO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static provider.common.CycleTools.generateUUID;

@Service
public class TicketService {
    @Autowired
    private UserDORepositoryImpl userDORepository;

    @Autowired
    private RedisTemplate redisTemplate;

    public String setTicket(UserVO user){
        if(ObjectUtils.isEmpty(user) || ObjectUtils.isEmpty(user.getUsername())){
            return null;
        }
        String ticket = generateUUID();
        UserDO userDO =  userDORepository.getByName(user.getUsername());
        if(ObjectUtils.isEmpty(userDO)){
            return null;
        }
        user = UserConvertor.convertorVO(userDO);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        TicketVO ticketVO = TicketVO.builder().ticket(ticket).userId(user.getId()).expired(date).status(1).build();
        /*redisTemplate.execute(jedis -> jedis.setex(ticket,
                24 * 3600, JsonTool.toJson(ticketVO)));*/
        return ticket;
    }

    public TicketVO getTicketVO(String ticket){
        if(ObjectUtils.isEmpty(ticket)){
            return null;
        }
        /*String ticketStr = redisTemplate.execute(jedis -> jedis.get(ticket));*/
        String ticketStr = "";
        return Optional.ofNullable(ticketStr).map(t -> JsonTool.fromJson(t, new TypeReference<TicketVO>() {}))
                .orElse(null);

    }

    public TicketVO setTicket(String ticket) {
        return null;
    }
}
