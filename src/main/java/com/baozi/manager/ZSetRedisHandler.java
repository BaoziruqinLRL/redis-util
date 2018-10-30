package com.baozi.manager;

import com.baozi.result.RedisOpResult;

/**
 * @Description: redis的ZSet操作类
 * @Author: baozi
 * @Create: 2018-09-13 18:34
 */
public interface ZSetRedisHandler<T> {

    /**
     * 存储一条数据
     * @param key redis的key
     * @param value redis的value
     * @param score 排序分数
     * @return 操作结果
     */
    RedisOpResult store(String key, T value, double score);

    /**
     * 移除集合中的指定一个或多个元素
     * @param key 集合的key
     * @param value 集合中的元素
     * @return 操作结果
     */
    RedisOpResult remove(String key,T... value);

    /**
     * 返回集合中指定元素的分数和排名
     * @param key 集合中的key
     * @param value 集合中的某个元素
     * @return 返回结果
     */
    RedisOpResult scoreRank(String key, T value);

    /**
     * 返回集合中指定索引区间内的值和分数，按照分数递增排序。不传区间默认[0,-1]即全查询
     * @param key 集合的key
     * @param start 起始索引
     * @param end 结束索引
     * @return 查询结果
     */
    RedisOpResult rangeValueWithScores(String key, Long start,Long end);

    /**
     * 查询集合中指定分数区间内的值和分数，按照分数递增排序。
     * @param key 集合的key
     * @param min 起始分数
     * @param max 结束分数
     * @return 查询结果
     */
    RedisOpResult rangeByScoreWithScores(String key, double min, double max);

    /**
     * 移除指定分数范围内的值
     * @param key 集合的key
     * @param min 起始分数
     * @param max 结束分数
     * @return 查询结果
     */
    RedisOpResult removeRangeByScore(String key,double min,double max);
}
