package com.baozi.manager;

import com.baozi.result.RedisOpResult;

/**
 * @Description:
 * @Author: lirl
 * @Create: 2018-09-12 19:33
 */
public interface HashRedisHandler<K, V> {

    /**
     * 存储一条数据
     * @param key 主key
     * @param hKey hashkey
     * @param hValue hashValue
     * @return 返回存储结果
     */
    RedisOpResult store(String key, K hKey, V hValue);

    /**
     * 获取key对应的hash
     * @param key 主key
     * @return 返回查询结果
     */
    RedisOpResult getValue(String key);

    /**
     * 获取key下面的hash中的指定hashValue
     * @param key 主key
     * @param hKey hashKey
     * @return 返回查询结果
     */
    RedisOpResult getHashValue(String key, K hKey);

    /**
     * 根据key和hKey批量获取hashValue
     * @param key 主key
     * @param hkeys hashKey
     * @return 返回查询结果
     */
    RedisOpResult getHValues(String key, K... hkeys);

    /**
     * 获取key下面的hash中的所有hashKey
     * @param key 主key
     * @return 返回查询结果
     */
    RedisOpResult getAllHKeys(String key);

    /**
     * 获取key下面的hash中的所有hashValue
     * @param key 主key
     * @return 返回查询结果
     */
    RedisOpResult getAllHValues(String key);

    /**
     * 根据key和hKey批量删除hashValue
     * @param key 主key
     * @param hkeys hashKey数组，可变参数
     * @return 返回删除结果
     */
    RedisOpResult deleteHValues(String key, K... hkeys);

    /**
     * 根据key删除指定hash
     * @param key 主key
     * @return 返回删除结果
     */
    RedisOpResult deleteValue(String key);
}
