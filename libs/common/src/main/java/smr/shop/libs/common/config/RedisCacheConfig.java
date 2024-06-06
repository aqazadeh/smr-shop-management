package smr.shop.libs.common.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisCacheConfig {


//    private final ObjectMapper objectMapper;
//
//    public RedisCacheConfig(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
//        redisTemplate.setConnectionFactory(connectionFactory);
//
////        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
//        return redisTemplate;
//    }
}
