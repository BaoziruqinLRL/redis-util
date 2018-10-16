package com.baozi.util;

import com.baozi.enumdata.RedisStatus;
import com.baozi.result.RedisOpResult;

/**
 * @Description: 工具包内部的通用实现
 * @Author: lirl
 * @Create: 2018-09-27 14:13
 */
public class RedisUtil {

    /**
     * 检查参数是否为空
     * @param params 可变参数
     * @return 返回结果，null则为查询成功，否则返回错误信息
     */
    public static boolean checkParamNotNull(Object... params){
        if (params == null){
            return false;
        }
        for (Object obj : params){
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    public static RedisOpResult buildErrorResult(String message, Exception exception, RedisStatus status){
        RedisOpResult errorResult = new RedisOpResult();
        errorResult.setCode(status);
        errorResult.setMessage(message);
        errorResult.setException(exception);
        return errorResult;
    }
}
