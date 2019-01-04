package com.baozi.manager;

import com.baozi.result.RedisOpResult;

import java.util.List;

/**
 * @Description: redis队列操作
 * @Author: baozi
 * @Create: 2018-11-13 17:43
 */
public interface ListRedisHandler<V> {

    /**
     * 返回列表指定位置的元素集合
     * @param key key值
     * @param startIndex 起始索引。不传默认为0
     * @param endIndex 结束索引。不传默认-1，表示所有元素
     * @return 查询结果
     */
    RedisOpResult range(String key, Long startIndex, Long endIndex);

    /**
     * 返回列表长度
     * @param key 列表的key
     * @return 返回操作结果
     */
    RedisOpResult size(String key);

    /**
     * 将一条数据插入头结点,只有当key存在才会插入，否则返回失败。从左插入，默认左边是头
     * @param key 列表的key
     * @param value 数据
     * @return 操作结果
     */
    RedisOpResult leftPush(String key, V value);

    /**
     * 将一批数据插入头结点
     * @param key 列表的key
     * @param values 数据列表
     * @return 操作结果
     */
    RedisOpResult leftPushAll(String key, List<V> values);

    /**
     * 删除元素。
     * 计数参数以下列方式影响操作：
     * count> 0：删除等于从头到尾移动的值的元素。
     * count <0：删除等于从尾到头移动的值的元素。
     * count = 0：删除等于value的所有元素。
     * @param key 列表的key
     * @param count 计数参数
     * @param value 删除的元素
     * @return 操作结果
     */
    RedisOpResult remove(String key, Long count, V value);

    /**
     * 从尾部弹出一个元素。
     * @param key 列表的值
     * @return 操作结果
     */
    RedisOpResult rightPop(String key);

    /**
     * 弹出列表的最后一个元素，并将该元素添加到另一个列表并返回
     * @param key 弹出列表的key
     * @param pushKey 添加列表的key
     * @return 操作结果
     */
    RedisOpResult rightPopAndLeftPush(String key, String pushKey);
}
