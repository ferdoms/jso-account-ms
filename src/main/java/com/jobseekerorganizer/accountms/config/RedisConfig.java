package com.jobseekerorganizer.accountms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;

import redis.clients.jedis.params.SetParams;

@Configuration
public class RedisConfig {
	@Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private Integer redisPort;

    @Value("${redis.pass}")
    private String redisPass;
    
    @Bean
    @Primary
    public JedisConnectionFactory jedisConnectionFactory() throws Exception {
    	JedisClientConfiguration clientConfig = JedisClientConfiguration.builder().usePooling().build();
    	RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(redisPort);
        return new JedisConnectionFactory(configuration, clientConfig);  	    	
    }
    
    @Bean
    public RedisTemplate<String, Object> regisTemplate() throws Exception {
    	final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
    	
    	template.setConnectionFactory(jedisConnectionFactory());
    	template.setKeySerializer(new StringRedisSerializer());
    	
    	template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    	template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    	
    	return template;
    }
}
