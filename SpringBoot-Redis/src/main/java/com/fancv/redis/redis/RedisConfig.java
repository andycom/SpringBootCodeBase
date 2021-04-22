package com.fancv.redis.redis;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Configuration(proxyBeanMethods = true)
@ConditionalOnClass(RedisOperations.class)
@AutoConfigureBefore(value = {RedisAutoConfiguration.class})
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    @Bean
    public Redis redisImpl(RedisTemplate<String, Object> redisTemplate) {
        return new RedisImpl(redisTemplate);
    }

    /**
     * 实例化 RedisTemplate 对象
     * 设置数据存入 redis 的序列化方式,并开启事务
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 如果不配置 Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    /**
     * redis 哨兵配置
     *
     * @return
     */
    @Bean
    public RedisSentinelConfiguration sentinelConfiguration(RedisProperties redisProperties) {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
        String master = sentinel.getMaster();
        List<String> nodes = sentinel.getNodes();
        String password = redisProperties.getPassword();
        //配置matser的名称
        redisSentinelConfiguration.master(master);
        //配置redis的哨兵sentinel
        Set<RedisNode> redisNodeSet = new HashSet<>();
        nodes.forEach(x -> {
            redisNodeSet.add(new RedisNode(x.split(":")[0], Integer.parseInt(x.split(":")[1])));
        });
        log.info("redis sentinel node set -->" + redisNodeSet);
        redisSentinelConfiguration.setSentinels(redisNodeSet);
        redisSentinelConfiguration.setPassword(password);
        redisSentinelConfiguration.setDatabase(redisProperties.getDatabase());
        return redisSentinelConfiguration;
    }

    /**
     * LettuceConnectionFactory 配置
     * <p>
     * 同时支持 JedisClusterConnection  配置
     * <p>
     * <p>
     * lettuce 和 Jedis  同级
     * <p>
     * 由spring-data-redis  提供
     *
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisSentinelConfiguration sentinelConfiguration) {


        //支持自适应集群拓扑刷新和静态刷新源
        ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                .enablePeriodicRefresh()
                .enableAllAdaptiveRefreshTriggers()
                .refreshPeriod(Duration.ofSeconds(5))
                .build();

        ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
                .topologyRefreshOptions(clusterTopologyRefreshOptions)
                .build();

        //从优先，读写分离，读从可能存在不一致，最终一致性CP
        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .clientOptions(clusterClientOptions)
                .build();

        return new LettuceConnectionFactory(sentinelConfiguration, lettuceClientConfiguration);
    }

}
