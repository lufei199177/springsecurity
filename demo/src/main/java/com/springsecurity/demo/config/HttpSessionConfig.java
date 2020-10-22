package com.springsecurity.demo.config;

/**
 * @author lufei
 * @date 2020/10/22
 * @desc 当前版本只要引入相关jar包，自动会配置成redis存储
 */
//@EnableRedisHttpSession
public class HttpSessionConfig {

    /*@Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    //SpringSessionBackedSessionRegistry是session为spring security提供的
    //用于在集群环境下控制会话并发的会话注册表实现类
    @Bean
    public SpringSessionBackedSessionRegistry<?> sessionRegistry(){
        return new SpringSessionBackedSessionRegistry<>(new RedisIndexedSessionRepository(redisTemplate));
    }

    //httpSession的事件监听，改用session提供的会话注册表
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }*/
}
