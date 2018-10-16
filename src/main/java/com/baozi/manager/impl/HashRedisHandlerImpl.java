package com.baozi.manager.impl;

import com.baozi.enumdata.RedisStatus;
import com.baozi.manager.HashRedisHandler;
import com.baozi.result.RedisObject;
import com.baozi.result.RedisOpResult;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static com.baozi.util.RedisUtil.buildErrorResult;
import static com.baozi.util.RedisUtil.checkParamNotNull;

/**
 * @Description:
 * @Author: lirl
 * @Create: 2018-09-12 19:30
 */
@Component
public class HashRedisHandlerImpl<K, V> implements HashRedisHandler<K, V> {

    @Resource
    private RedisTemplate<String,V> redisTemplate;

    private static final int DEFAULT_INFLU_NUM = 1;

    @Override
    public RedisOpResult store(String key, K hKey, V hValue) {
        if (!checkParamNotNull(key,hKey,hValue)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            redisTemplate.opsForHash().put(key,hKey,hValue);
            result = buildSuccessResult(hValue,DEFAULT_INFLU_NUM,null,null);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult getValue(String key) {
        if (!checkParamNotNull(key)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            Map value = redisTemplate.opsForHash().entries(key);
            result = buildSuccessResult(null,DEFAULT_INFLU_NUM,value,null);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public RedisOpResult getHashValue(String key, K hKey) {
        if (!checkParamNotNull(key)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            V v = (V) redisTemplate.opsForHash().get(key,hKey);
            result = buildSuccessResult(v,DEFAULT_INFLU_NUM,null,null);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult getHValues(String key, K... hkeys) {
        if (!checkParamNotNull(key,hkeys)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            List value = redisTemplate.opsForHash().multiGet(key, Arrays.asList(hkeys));
            result = buildSuccessResult(null,value.size(),null,value);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult getAllHKeys(String key) {
        if (!checkParamNotNull(key)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            Set value = redisTemplate.opsForHash().keys(key);
            result = buildSuccessResult(null,value.size(),null,new ArrayList(value));
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult getAllHValues(String key) {
        if (!checkParamNotNull(key)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            List value = redisTemplate.opsForHash().values(key);
            result = buildSuccessResult(null,value.size(),null,value);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult deleteHValues(String key, K... hkeys) {
        if (!checkParamNotNull(key,hkeys)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            Long value = redisTemplate.opsForHash().delete(key,hkeys);
            result = buildSuccessResult(null,value.intValue(),null,null);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult deleteValue(String key) {
        if (!checkParamNotNull(key)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            boolean value = redisTemplate.delete(key);
            if (value){
                result = buildSuccessResult(null,DEFAULT_INFLU_NUM,null,null);
            }else{
                result = buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
            }

        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    private RedisOpResult buildSuccessResult(V value, Integer influNum, Map entry, List list){
        RedisOpResult buildSuccessResult = new RedisOpResult();
        buildSuccessResult.setCode(RedisStatus.SUCCESS);
        buildSuccessResult.setMessage(RedisStatus.SUCCESS.getMsg());
        buildSuccessResult.setOpCount(influNum);
        buildSuccessResult.setValues(Collections.singletonList(new RedisObject(value,entry,list)));

        return buildSuccessResult;
    }
}
