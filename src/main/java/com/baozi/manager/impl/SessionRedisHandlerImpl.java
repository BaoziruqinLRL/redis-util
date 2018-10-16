package com.baozi.manager.impl;

import com.baozi.enumdata.RedisStatus;
import com.baozi.manager.HashRedisHandler;
import com.baozi.manager.SessionRedisHandler;
import com.baozi.result.RedisOpResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.baozi.util.RedisUtil.buildErrorResult;
import static com.baozi.util.RedisUtil.checkParamNotNull;

/**
 * @Description:
 * @Author: lirl
 * @Create: 2018-09-27 14:09
 */
@Component
public class SessionRedisHandlerImpl<V> implements SessionRedisHandler {

    @Resource
    private HashRedisHandler<String,V> hashRedisHandler;

    private static final String SESSION_TAG = "laidantech_session:sessions:";
    private static final String SESSION_HASH_TAG = "sessionAttr:";

    @Override
    public RedisOpResult getSessionAttr(String sessionId,String sessionType) {
        if (!checkParamNotNull(sessionId,sessionType)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        String sessionKey = buildSessionKey(sessionId);
        String sessionHashKey = buildSessionHashKey(sessionType);
        return hashRedisHandler.getHashValue(sessionKey,sessionHashKey);
    }

    private String buildSessionKey(String sessionId){
        return SESSION_TAG + sessionId;
    }

    private String buildSessionHashKey(String sessionType){
        return SESSION_HASH_TAG + sessionType;
    }
}
