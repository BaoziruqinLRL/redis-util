package com.baozi.manager.impl;

import com.baozi.enumdata.RedisStatus;
import com.baozi.manager.ObjectRedisHandler;
import com.baozi.result.RedisObject;
import com.baozi.result.RedisOpResult;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static com.baozi.util.RedisUtil.buildErrorResult;

/**
 * @Description: redis的opsForValue操作，在缓存中操作字符串类型的数据
 * @Author: baozi
 * @Create: 2018-09-12 18:03
 */
@Component
public class ObjectRedisHandlerImpl<T> implements ObjectRedisHandler<T> {

    @Resource
    private RedisTemplate<String,T> redisTemplate;

    private static final String DEFAULT_MISS_KEY = "key can't not be null";
    private static final String DEFAULT_MISS_VALUE = "value can't not be null";
    private static final Double DEFAULT_SCORE = 0D;
    private static final Long DEFAULT_RANK = 0L;

    /**
     * 默认过期时间，24小时
     */
    private static final Long DEFAULT_EXPIRE_TIME = 86400L;
    /**
     * 默认时间单位，秒
     */
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    @Override
    public RedisOpResult store(String key, T value,Long expireTime,TimeUnit timeUnit) {
        if (key == null){
            return buildErrorResult(DEFAULT_MISS_KEY,null,RedisStatus.FAIL);
        }
        if (value == null){
            return buildErrorResult(DEFAULT_MISS_VALUE,null,RedisStatus.FAIL);
        }
        // 设置默认过期时间，24小时
        if (expireTime == null){
            expireTime = DEFAULT_EXPIRE_TIME;
        }
        // 设置默认的时间单位为秒
        if (timeUnit == null){
            timeUnit = DEFAULT_TIME_UNIT;
        }
        RedisOpResult result;
        try{
            if (expireTime == -1L){
                redisTemplate.opsForValue().set(key, value);
            }else {
                redisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
            }
            result = buildSuccessResult(value);
        }catch(Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult store(String key, T value) {
        return this.store(key,value,null,null);
    }

    @Override
    public RedisOpResult get(String key) {
        if (key == null){
            return buildErrorResult(DEFAULT_MISS_KEY,null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            var value = redisTemplate.opsForValue().get(key);
            result = buildSuccessResult(value);
        }catch(Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult delete(String key) {
        if (key == null){
            return buildErrorResult(DEFAULT_MISS_KEY,null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            redisTemplate.delete(key);
            result = buildSuccessResult(null);
        }catch(Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    private RedisOpResult buildSuccessResult(T value){
        RedisOpResult buildSuccessResult = new RedisOpResult();
        buildSuccessResult.setCode(RedisStatus.SUCCESS);
        buildSuccessResult.setMessage(RedisStatus.SUCCESS.getMsg());
        buildSuccessResult.setValues(Collections.singletonList(new RedisObject(value,DEFAULT_SCORE,DEFAULT_RANK)));
        buildSuccessResult.setOpCount(1);
        return buildSuccessResult;
    }
}
