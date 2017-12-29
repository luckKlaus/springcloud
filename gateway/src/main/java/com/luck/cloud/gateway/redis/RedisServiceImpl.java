package com.luck.cloud.gateway.redis;

import com.luck.common.utils.JSONUtilGjson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements IRedisService {

    private static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    private static final long DEFAULT_WAIT_LOCK_TIME_OUT = 60000;//60s 有慢sql，超时时间设置长一点
    private static final long DEFAULT_EXPIRE = 80;//80s 有慢sql，超时时间设置长一点


    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    public boolean set(final String key, final String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    public String get(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    @Override
    public Long del(final String key) {
        Long result = redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                return connection.del(serializer.serialize(key));
            }
        });
        return result;
    }

    public boolean expire(final String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    public <T> boolean setList(String key, List<T> list) {
        String value = JSONUtilGjson.toJson(list);
        return set(key, value);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clz) {
        String json = get(key);
        if (json != null) {
            List<T> list = JSONUtilGjson.toList(json, clz);
            return list;
        }
        return null;
    }

    @Override
    public long lpush(final String key, Object obj) {
        final String value = JSONUtilGjson.toJson(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    @Override
    public long rpush(final String key, Object obj) {
        final String value = JSONUtilGjson.toJson(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    @Override
    public String lpop(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] res = connection.lPop(serializer.serialize(key));
                return serializer.deserialize(res);
            }
        });
        return result;
    }

    @Override
    public void lock(String key, long timeout) {
        String lockKey = generateLockKey(key);
        long start = System.currentTimeMillis();
        long expires = System.currentTimeMillis() + timeout + 1;
        String expiresStr = String.valueOf(expires);
        try {
            while ((System.currentTimeMillis() - start) < timeout) {
                if (redisTemplate.getConnectionFactory().getConnection().setNX(lockKey.getBytes(), expiresStr.getBytes())) {
                    redisTemplate.expire(lockKey, DEFAULT_EXPIRE, TimeUnit.SECONDS);//暂设置为80s过期，防止异常中断锁未释放
                    if (logger.isDebugEnabled()) {
                        logger.debug("add RedisLock[" + key + "].");
                    }
                    break;
                }
                TimeUnit.SECONDS.sleep(3);
            }
        } catch (Exception e) {
            unlock(lockKey);
        }
    }

    @Override
    public void unlock(String key) {

        String lockKey = generateLockKey(key);
        if (logger.isDebugEnabled()) {
            logger.debug("release RedisLock[" + lockKey + "].");
        }
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.del(lockKey.getBytes());
        connection.close();

    }

    private String generateLockKey(String key) {
        return String.format("LOCK:%s", key);
    }

    public void lock(String key) {
        lock(key, DEFAULT_WAIT_LOCK_TIME_OUT);
    }

}
