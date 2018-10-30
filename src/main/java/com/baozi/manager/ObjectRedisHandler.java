package com.baozi.manager;

import com.baozi.result.RedisOpResult;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: baozi
 * @Create: 2018-09-12 18:02
 */
public interface ObjectRedisHandler<T> {

    /**
     * 存储一条数据
     * @param key redis的key
     * @param value redis的value
     * @param expireTime 缓存时间，单位为秒; 传-1表示不设置过期时间
     * @param timeUnit 时间单位，不传默认设置为秒
     * @return 返回操作结果
     */
    RedisOpResult store(String key, T value, Long expireTime, TimeUnit timeUnit);

    /**
     * 存储一条数据,使用默认的过期时间和时间单位
     * @param key redis的key
     * @param value redis的value
     * @return 返回操作结果
     */
    RedisOpResult store(String key, T value);

    /**
     * 查询指定key的数据
     * @param key redis的key
     * @return 返回查询到的结果
     */
    RedisOpResult get(String key);

    /**
     * 删除指定key的数据
     * @param key redis的key
     * @return 返回操作结果
     */
    RedisOpResult delete(String key);
}
