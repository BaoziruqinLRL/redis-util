package com.baozi.manager.impl;

import com.baozi.enumdata.RedisStatus;
import com.baozi.manager.ListRedisHandler;
import com.baozi.result.RedisObject;
import com.baozi.result.RedisOpResult;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static com.baozi.util.RedisUtil.buildErrorResult;
import static com.baozi.util.RedisUtil.checkParamNotNull;


/**
 * @Description:
 * @Author: lirl
 * @Create: 2018-11-13 19:11
 */
@Component
public class ListRedisHandlerImpl<V> implements ListRedisHandler<V> {

    @Resource
    private RedisTemplate<String,V> redisTemplate;

    private static final long DEFAULT_INFLU_NUM = 1;

    @Override
    public RedisOpResult range(String key, Long startIndex, Long endIndex) {
        if (!checkParamNotNull(key)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        if (startIndex == null){
            startIndex = 0L;
        }
        if (endIndex == null){
            endIndex = -1L;
        }
        RedisOpResult result;
        try{
            List<V> list = redisTemplate.opsForList().range(key,startIndex,endIndex);
            result = buildSuccessResult(list,DEFAULT_INFLU_NUM,null);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult size(String key) {
        if (!checkParamNotNull(key)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            Long size = redisTemplate.opsForList().size(key);
            result = buildSuccessResult(null,null,size);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult leftPush(String key, V value) {
        if (!checkParamNotNull(key,value)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            Long size = redisTemplate.opsForList().leftPush(key,value);
            result = buildSuccessResult(null,DEFAULT_INFLU_NUM,size);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult leftPushAll(String key, List<V> values) {
        if (!checkParamNotNull(key,values)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            Long size = redisTemplate.opsForList().leftPushAll(key,values);
            result = buildSuccessResult(null,(long) values.size(),size);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult remove(String key, Long count, V value) {
        if (!checkParamNotNull(key,value)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        if (count == null){
            count = 0L;
        }
        RedisOpResult result;
        try{
            Long size = redisTemplate.opsForList().remove(key,count,value);
            result = buildSuccessResult(null, DEFAULT_INFLU_NUM, size);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult rightPop(String key) {
        if (!checkParamNotNull(key)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            V value = redisTemplate.opsForList().rightPop(key);
            result = buildSuccessResult(Collections.singletonList(value), DEFAULT_INFLU_NUM, null);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult rightPopAndLeftPush(String key, String pushKey) {
        if (!checkParamNotNull(key)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            V value = redisTemplate.opsForList().rightPopAndLeftPush(key,pushKey);
            result = buildSuccessResult(Collections.singletonList(value), DEFAULT_INFLU_NUM, null);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    private RedisOpResult buildSuccessResult(List<V> values,Long influNum,Long size){
        RedisOpResult buildSuccessResult = new RedisOpResult();
        buildSuccessResult.setCode(RedisStatus.SUCCESS);
        buildSuccessResult.setMessage(RedisStatus.SUCCESS.getMsg());
        buildSuccessResult.setOpCount(influNum);
        buildSuccessResult.setSize(size);
        if (values != null) {
            try {
                buildSuccessResult.setValues(Collections.singletonList(new RedisObject(values)));
            } catch (Exception ex) {
                buildSuccessResult.setValues(null);
            }
        }
        return buildSuccessResult;
    }
}
