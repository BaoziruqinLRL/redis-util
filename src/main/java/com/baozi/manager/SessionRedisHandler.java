package com.baozi.manager;

import com.baozi.result.RedisOpResult;

/**
 * @Description: redisSession操作
 * @Author: baozi
 * @Create: 2018-09-27 14:07
 */
public interface SessionRedisHandler {

    /**
     * 根据sessionId获取session数据对象
     * @param sessionId 会话id
     * @param sessionType 会话类型
     * @return 返回查询结果
     */
    RedisOpResult getSessionAttr(String sessionId,String sessionType);
}
