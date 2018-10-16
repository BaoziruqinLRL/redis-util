package com.baozi.enumdata;

/**
 * @Description: redis operation result type
 * @Author: lirl
 * @Create: 2018-09-14 13:55
 */
public enum RedisStatus {
    /**
     * 成功结果
     */
    SUCCESS("operation success",200),
    /**
     * 错误结果
     */
    FAIL("operation error",400),
    /**
     * 异常结果

     */
    EXCEPTION("operation exception",500);

    private String msg;
    private int code;

    RedisStatus(String msg,int code){
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
