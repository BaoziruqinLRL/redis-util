package com.baozi.result;

import com.baozi.enumdata.RedisStatus;

import java.util.List;

/**
 * @Description: redis结果集
 * @Author: baozi
 * @Create: 2018-09-12 18:08
 */
public class RedisOpResult {

    /**
     * 操作结果
     */
    private RedisStatus code;

    /**
     * 操作结果
     */
    private String message;

    /**
     * 异常
     */
    private Exception exception;

    /**
     * 数据集合
     */
    private List<RedisObject> values;

    /**
     * 操作影响数据数量
     */
    private long opCount;

    public long getOpCount() {
        return opCount;
    }

    public void setOpCount(long opCount) {
        this.opCount = opCount;
    }

    public void setCode(RedisStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public RedisObject getValue(){
        if (values == null || values.size() == 0){
            return null;
        }
        return values.get(0);
    }

    public boolean isSuc(){
        if (code == RedisStatus.SUCCESS){
            return true;
        }else{
            return false;
        }
    }

    public List<RedisObject> getValues() {
        return values;
    }

    public void setValues(List<RedisObject> values) {
        this.values = values;
    }
}
