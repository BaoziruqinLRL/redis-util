package com.baozi.manager.impl;

import com.baozi.enumdata.RedisStatus;
import com.baozi.manager.ZSetRedisHandler;
import com.baozi.result.RedisObject;
import com.baozi.result.RedisOpResult;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.baozi.util.RedisUtil.buildErrorResult;
import static com.baozi.util.RedisUtil.checkParamNotNull;

/**
 * @Description: redis的ZSet操作实现
 * @Author: lirl
 * @Create: 2018-09-13 18:35
 */
@Component
public class ZSetRedisHandlerImpl<T> implements ZSetRedisHandler<T> {

    @Resource
    private RedisTemplate<String,T> redisTemplate;

    private static final long DEFAULT_INFLU_NUM = 1;
    private static final Double DEFAULT_SCORE = 0D;
    private static final Long DEFAULT_RANK = 0L;

    @Override
    public RedisOpResult store(String key, T value, double score) {
        if (!checkParamNotNull(key,value,score)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            redisTemplate.opsForZSet().add(key,value,score);
            result = buildSuccessResult(value,score,DEFAULT_RANK,DEFAULT_INFLU_NUM);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult remove(String key, T... value) {
        if (!checkParamNotNull(key)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try {
            long influNum = 0;
            if (value == null) {
                redisTemplate.delete(key);
            }else{
                redisTemplate.opsForZSet().remove(key,value);
                influNum = value.length;
            }
            result = buildSuccessResult(null,DEFAULT_SCORE,DEFAULT_RANK,influNum);
            if (value != null) {
                List<RedisObject> obs = new ArrayList<>();
                for (T t : value) {
                    obs.add(new RedisObject(t, DEFAULT_SCORE, DEFAULT_RANK));
                }
                result.setValues(obs);
            }
        }catch(Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult scoreRank(String key, T value) {
        if (!checkParamNotNull(key,value)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            Double score = redisTemplate.opsForZSet().score(key,value);
            Long rank = redisTemplate.opsForZSet().rank(key,value);
            result = buildSuccessResult(value,score,rank,DEFAULT_INFLU_NUM);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    @Override
    public RedisOpResult rangeValueWithScores(String key, Long start, Long end) {
        if (!checkParamNotNull(key)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result = null;
        try{
            // 索引为空则默认置为全范围查询
            if (start == null || end == null){
                start = 0L;
                end = -1L;
            }
            Set<ZSetOperations.TypedTuple<T>> values = redisTemplate.opsForZSet().rangeWithScores(key,start,end);
            if (values != null) {
                List<RedisObject> obs = values.stream()
                        .map(x -> new RedisObject(x.getValue(), x.getScore(), DEFAULT_RANK))
                        .collect(Collectors.toList());
                result = buildSuccessResult(null, DEFAULT_SCORE, DEFAULT_RANK, (long) values.size());
                result.setValues(obs);
            }
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return (result == null)?new RedisOpResult():result;
    }

    @Override
    public RedisOpResult rangeByScoreWithScores(String key, double min, double max) {
        if (!checkParamNotNull(key)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result = null;
        try{
            Set<ZSetOperations.TypedTuple<T>> values = redisTemplate.opsForZSet().rangeByScoreWithScores(key,min,max);
            if (values != null) {
                List<RedisObject> obs = values.stream()
                        .map(x -> new RedisObject(x.getValue(), x.getScore(), DEFAULT_RANK))
                        .collect(Collectors.toList());
                result = buildSuccessResult(null, DEFAULT_SCORE, DEFAULT_RANK, (long) values.size());
                result.setValues(obs);
            }
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return (result==null)?new RedisOpResult():result;
    }

    @Override
    public RedisOpResult removeRangeByScore(String key, double min, double max) {
        if (!checkParamNotNull(key)){
            return buildErrorResult(RedisStatus.FAIL.getMsg(),null,RedisStatus.FAIL);
        }
        RedisOpResult result;
        try{
            Long influNum = redisTemplate.opsForZSet().removeRangeByScore(key,min,max);
            result = buildSuccessResult(null,DEFAULT_SCORE,DEFAULT_RANK,influNum);
        }catch (Exception ex){
            result = buildErrorResult(ex.getMessage(),ex,RedisStatus.EXCEPTION);
        }
        return result;
    }

    private RedisOpResult buildSuccessResult(T value,Double score,Long rank,Long influNum){
        RedisOpResult buildSuccessResult = new RedisOpResult();
        buildSuccessResult.setCode(RedisStatus.SUCCESS);
        buildSuccessResult.setMessage(RedisStatus.SUCCESS.getMsg());
        buildSuccessResult.setOpCount(influNum);
        if (value != null) {
            try {
                buildSuccessResult.setValues(Collections.singletonList(new RedisObject(value,score,rank)));
            } catch (Exception ex) {
                buildSuccessResult.setValues(null);
            }
        }
        return buildSuccessResult;
    }
}

