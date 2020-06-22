package provider.service;

import provider.common.CycleErrorCode;
import provider.common.RedisKeyUtil;
import provider.request.DataRequest;
import provider.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DataService {

    @Autowired
    private RedisTemplate redisTemplate;

    private SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

    public void recordUV(String ip) {
        String redisKey = RedisKeyUtil.getUVKey(sf.format(new Date()));
        redisTemplate.opsForHyperLogLog().add(redisKey, ip);
    }

    public BaseResponse getUV(DataRequest request) {
        return getUV(request.getStartDate(), request.getEndDate());

    }

    private BaseResponse getUV(Date start, Date end) {
        if (start == null || end == null) {
            return CycleErrorCode.REQUEST_MISSING.getResponse("参数不能为空!");
        }

        List<String> keyList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while (!calendar.getTime().after(end)) {
            String key = RedisKeyUtil.getUVKey(sf.format(calendar.getTime()));
            keyList.add(key);
            calendar.add(Calendar.DATE, 1);
        }

        String redisKey = RedisKeyUtil.getUVKey(sf.format(start), sf.format(end));
        redisTemplate.opsForHyperLogLog().union(redisKey, keyList.toArray());

        return BaseResponse.builder().success(true).result(redisTemplate.opsForHyperLogLog().size(redisKey)).build();
    }

    public void recordDAU(int userId) {
        String redisKey = RedisKeyUtil.getDAUKey(sf.format(new Date()));
        redisTemplate.opsForValue().setBit(redisKey, userId, true);
    }

    public BaseResponse getDAU(DataRequest request) {
        return getDAU(request.getStartDate(), request.getEndDate());
    }

    private BaseResponse getDAU(Date start, Date end) {
        if (start == null || end == null) {
            return CycleErrorCode.REQUEST_MISSING.getResponse("参数不能为空!");
        }

        List<byte[]> keyList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while (!calendar.getTime().after(end)) {
            String key = RedisKeyUtil.getDAUKey(sf.format(calendar.getTime()));
            keyList.add(key.getBytes());
            calendar.add(Calendar.DATE, 1);
        }

        return (BaseResponse) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                String redisKey = RedisKeyUtil.getDAUKey(sf.format(start), sf.format(end));
                connection.bitOp(RedisStringCommands.BitOperation.OR,
                        redisKey.getBytes(), keyList.toArray(new byte[0][0]));
                return  BaseResponse.builder().success(true).result(connection.bitCount(redisKey.getBytes())).build();
            }
        });
    }

}
