package provider.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * @authorgouhuo on 2020/04/27.
 */
public class CycleTools {
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
