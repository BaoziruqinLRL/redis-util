package com.baozi.result;

import java.util.List;
import java.util.Map;

/**
 * @Description: redis操作数据集
 * @Author: lirl
 * @Create: 2018-09-14 15:33
 */
public class RedisObject {

    public RedisObject(Object value){
        this(value,0D,0L,null,null);
    }

    public RedisObject(Object value,Map entry,List list){
        this(value,0D,0L,entry,list);
    }

    public RedisObject(Object value, Double score, Long rank) {
        this(value,score,rank,null,null);
    }

    public RedisObject(Object value, Double score, Long rank,Map entry,List list){
        this.value = value;
        this.score = score;
        this.rank = rank;
        this.entry = entry;
        this.list = list;
    }

    /**
     * 数据
     */
    private Object value;

    /**
     * 排名分数，适用于ZSet操作的数据
     */
    private Double score;

    /**
     * 排名序号
     */
    private Long rank;

    /**
     * 哈希结果值
     */
    private Map entry;

    /**
     * 列表结果值
     */
    private List list;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Map getEntry() {
        return entry;
    }

    public void setEntry(Map entry) {
        this.entry = entry;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
