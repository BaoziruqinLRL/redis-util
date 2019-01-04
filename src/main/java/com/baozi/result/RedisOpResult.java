package com.baozi.result;

import com.baozi.enumdata.RedisStatus;
import lombok.Data;

import java.util.List;

/**
 * @Description: redis结果集
 * @Author: baozi
 * @Create: 2018-09-12 18:08
 */
@Data
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

    /**
     * 数据长度
     */
    private Long size;

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
