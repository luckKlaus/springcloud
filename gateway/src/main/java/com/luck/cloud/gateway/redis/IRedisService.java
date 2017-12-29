package com.luck.cloud.gateway.redis;

import java.util.List;

/**
 * Created by luck on 2017/09/28.
 */
public interface IRedisService {

    /**
     * 设置键值对
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, String value);

    /**
     * 根据单个键获取值
     * @param key
     * @return
     */
    public String get(String key);
    
    /**
     * 删除指定键
     * @param key
     * @return
     */
    public Long del(String key);

    public boolean expire(String key, long expire);

    public <T> boolean setList(String key, List<T> list);

    public <T> List<T> getList(String key, Class<T> clz);

    public long lpush(String key, Object obj);

    public long rpush(String key, Object obj);

    public String lpop(String key);

    public void lock(String key, long timeout);

    public void unlock(String key);

}
