package com.baozi.enumdata;

import java.io.Serializable;

/**
 * 这个类表示缓存的数据是一个空值,存的价值防止缓存在查询不到数据时一直穿透.
 * <p>
 * @version 1.0 2018-6-27 12:09:28
 * @since 1.5
 * @author dongbin
 */
public class EmptyValue implements Serializable {

    private static final EmptyValue EMPTY_VALUE = new EmptyValue();

    private EmptyValue() {
    }

    /**
     * 得到一个空值实例.
     *
     * @return 空值实例.
     */
    public static final EmptyValue getEmtpyValue() {
        return EMPTY_VALUE;
    }
}
