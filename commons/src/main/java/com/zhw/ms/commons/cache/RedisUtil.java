package com.zhw.ms.commons.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zhw.ms.commons.spring.SpringUtil;
import com.zhw.ms.commons.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Created by ZHW on 2015/7/16.
 */
public class RedisUtil {

    private static StringRedisTemplate template;

    static {
        if (template == null) {
            template = SpringUtil.getBean("redisTemplate");
        }
    }

    /**
     * 获取list
     *
     * @param listName key
     * @return BoundListOperations bound list operations
     */
    public static BoundListOperations<String, String> list(String listName) {
        return template.boundListOps(listName);
    }

    /**
     * 获取hash
     *
     * @param hashName hash name
     * @return BoundHashOperations bound hash operations
     */
    public static BoundHashOperations<String, String, String> hash(String hashName) {
        return template.boundHashOps(hashName);
    }

    /**
     * 设置value值
     *
     * @param hashName hash name
     * @param key      key
     * @param value    value
     * @param timeout  超时时间
     */
    public static void putHash(String hashName, String key, String value, long timeout) {
        BoundHashOperations<String, String, String> operations = template.boundHashOps(hashName);
        operations.put(key, value);
        operations.expire(timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取value值
     *
     * @param hashName hash name
     * @param key      key
     * @return value hash
     */
    public static String getHash(String hashName, String key) {
        return (String) template.boundHashOps(hashName).get(key);
    }

    /**
     * 获取set
     *
     * @param setName key
     * @return BoundSetOperations bound set operations
     */
    public static BoundSetOperations<String, String> set(String setName) {
        return template.boundSetOps(setName);
    }

    /**
     * 设置value值
     *
     * @param setName key
     * @param value   value
     * @param timeout 超时时间
     */
    public static void addSet(String setName, String value, long timeout) {
        BoundSetOperations<String, String> operations = template.boundSetOps(setName);
        operations.add(value);
        operations.expire(timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取value值
     *
     * @param key key
     * @return value string
     */
    public static String popSet(String key) {
        return template.boundSetOps(key).pop();
    }

    /**
     * 获取zset
     *
     * @param zsetName key
     * @return BoundZSetOperations bound z set operations
     */
    public static BoundZSetOperations<String, String> zset(String zsetName) {
        return template.boundZSetOps(zsetName);
    }

    /**
     * 获取value
     *
     * @param key key
     * @return BoundValueOperations bound value operations
     */
    public static BoundValueOperations<String, String> value(String key) {
        return template.boundValueOps(key);
    }

    /**
     * 设置value值
     *
     * @param key     key
     * @param value   value
     * @param timeout 超时时间
     */
    public static void setValue(String key, String value, long timeout) {
        BoundValueOperations<String, String> operations = template.boundValueOps(key);
        operations.set(value);
        operations.expire(timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取value值
     *
     * @param key key
     * @return value value
     */
    public static String getValue(String key) {
        return template.boundValueOps(key).get();
    }

    /**
     * 分布式锁
     *
     * @param key     key
     * @param timeout 超时时间
     */
    public static void setIfAbsent(String key, long timeout) {
        BoundValueOperations<String, String> operations = template.boundValueOps(key);
        operations.setIfAbsent("1");
        operations.expire(timeout, TimeUnit.SECONDS);

    }

    /**
     * 分布式锁
     *
     * @param key key
     */
    public static void setIfAbsent(String key) {
        BoundValueOperations<String, String> operations = template.boundValueOps(key);
        operations.setIfAbsent("1");
    }

    /**
     * 删除
     *
     * @param key key
     */
    public static void remove(String key) {
        template.delete(key);
    }

    /**
     * 初始化原子加变量
     *
     * @param key   key
     * @param value value
     * @return 是否初始化 boolean
     */
    public static boolean initIfBlank(String key, long value) {
        //初始化key的值
        if (StringUtils.isBlank(value(key).get(0, -1))) {
            value(key).increment(value);
            return true;
        }

        return false;
    }

    /**
     * Execute string.
     *
     * @param scripte the scripte
     * @param key     the key
     * @param args    the args
     * @return the string
     */
    public static String execute(String scripte, List<String> key, Object... args) {

        Object[] strArgs = new String[args.length];
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                Object item = args[i];
                if (item == null) {
                    strArgs[i] = null;
                } else if (item instanceof String) {
                    strArgs[i] = item;
                } else {
                    strArgs[i] = JsonUtil.entity2Json(item);
                }
            }
        }
        return template.execute(new DefaultRedisScript<String>(scripte, String.class), key, strArgs);

    }

    /**
     * 比较当前值增加后是否超过指定最大值，如果没超过最大值则增加，否则不增加     *
     *
     * @param key      key
     * @param addValue 增加的值
     * @param maxValue 最大值
     * @return 0 ：表示增加成功，小于0表示还缺多少
     */
    public static Integer compareAndAdd(String key, Integer addValue, Integer maxValue) {

        return compareAndAdd(key, addValue, maxValue, 0L);
    }


    /**
     * 比较当前值增加后是否超过指定最大值，如果没超过最大值则增加，否则不增加
     *
     * @param key          key
     * @param addValue     增加的值
     * @param maxValue     最大值
     * @param expireSecond 超时秒数
     * @return 0 ：表示增加成功，小于0表示还缺多少
     */
    public static Integer compareAndAdd(String key, Integer addValue, Integer maxValue, Long expireSecond) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("key不能为空");
        }
        if (addValue == null || addValue <= 0 || maxValue == null || maxValue <= 0) {
            throw new IllegalArgumentException("addValue,maxValue不能小于0 或者为空");
        }
        String script = "local key, expire,addValue,maxValue = KEYS[1] ,tonumber(ARGV[1]), tonumber(ARGV[2]),tonumber(ARGV[3]);\n" +
                "if   redis.call('exists',key) == 0 then\n" +
                "    if maxValue >= addValue then\n" +
                "       redis.call('set',key,addValue);\n" +
                "       if expire>0 then \n" +
                "           redis.call('EXPIRE',key,expire);\n" +
                "       end; \n" +
                "        return '0';\n" +
                "    end;\n " +
                "    return ''..(maxValue-addValue)..'';\n" +
                "else\n" +
                "    local oldValue =   redis.call('get',key);\n" +
                "    if oldValue + addValue <= maxValue then\n" +
                "        local resultValue= redis.call('incrby',key,addValue);\n" +
                "        return '0';\n" +
                "    end;\n" +
                "    return ''..(maxValue -oldValue- addValue )..'';\n" +
                "end;";
        return Integer.parseInt(RedisUtil.execute(script, Arrays.asList(key), "" + expireSecond, addValue, maxValue));
    }
    /**
     * 比较当前值增加后是否超过指定最大值，如果没超过最大值则增加，否则不增加
     *
     * @param key          key
     * @param addValue     增加的值
     * @param maxValue     最大值
     *                     @param  leftMinValue 最少需要剩余的只
     * @param expireSecond 超时秒数
     * @return 0 ：表示增加成功;小于0表示还缺多少 ;大于零是表示失败,值表示多多少(比leftMinValue少)
     */
    public static Integer compareAndAdd(String key, Integer addValue, Integer maxValue,Integer leftMinValue, Long expireSecond) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("key不能为空");
        }
        if (addValue == null || addValue <= 0 || maxValue == null || maxValue <= 0) {
            throw new IllegalArgumentException("addValue,maxValue不能小于0 或者为空");
        }
        if(leftMinValue==null||leftMinValue.compareTo(0)<0){
            leftMinValue=0;
        }
        String script = "local key, expire,addValue,maxValue,leftMinValue = KEYS[1] ,tonumber(ARGV[1]), tonumber(ARGV[2]),tonumber(ARGV[3]),tonumber(ARGV[4]);\n" +
                "if   redis.call('exists',key) == 0 then\n" +
                "    if maxValue >= addValue and (maxValue>=(addValue+leftMinValue) or (maxValue== addValue)) then\n" +
                "       redis.call('set',key,addValue);\n" +
                "       if expire>0 then \n" +
                "           redis.call('EXPIRE',key,expire);\n" +
                "       end; \n" +
                "        return '0';\n" +
                "    end;\n " +
                "    return ''..(maxValue-addValue)..'';\n" +
                "else\n" +
                "    local oldValue =   redis.call('get',key);\n" +
                "    if (oldValue + addValue) <= maxValue and (maxValue>=(oldValue+addValue+leftMinValue) or maxValue== (oldValue+addValue)) then\n" +
                "        local resultValue= redis.call('incrby',key,addValue);\n" +
                "        return '0';\n" +
                "    end;\n" +
                "    return ''..(maxValue -oldValue- addValue )..'';\n" +
                "end;";
        return Integer.parseInt(RedisUtil.execute(script, Arrays.asList(key), "" + expireSecond, addValue, maxValue,leftMinValue));
    }
    /**
     * 原子操作设置值并且设置超时时间
     *
     * @param key          key
     * @param value        值
     * @param expireSecond 超时秒数
     * @return 0 ：表示增加成功，小于0表示还缺多少
     */
    public static boolean setValueAndExpire(String key, String value, Long expireSecond) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("key不能为空");
        }
        if (value == null) {
            throw new IllegalArgumentException("value不能为空");
        }
        String script = "local key, value,expire  = KEYS[1] ,ARGV[1], tonumber(ARGV[2]);\n" +
                "redis.call('set',key,value);\n" +
                "if expire>0 then \n" +
                "    redis.call('EXPIRE',key,expire);\n" +
                "end;\n" +
                "return '1'; \n";

        String result = RedisUtil.execute(script, Arrays.asList(key), value, "" + expireSecond);
        return result.equals("1");
    }

    /**
     * 如果值不存在则设置值，否知直接从缓存中读取
     *
     * @param key      the key
     * @param supplier the 读取数据的操作
     * @param timeOut  the 超时时间
     * @param timeUnit the 超时时间单位
     * @return 返回当前key的值。
     */
    public static <T> T setIfNotExists(String key, Supplier<T> supplier, Long timeOut, TimeUnit timeUnit, Class clazz) {
        T resultObj = null;
        String result = getValue(key);
        if (StringUtils.isEmpty(result)) {
            T supplierResult = supplier.get();
            if (supplierResult != null) {
                setValueAndExpire(key, JsonUtil.entity2Json(supplierResult), timeUnit.toSeconds(timeOut));
                resultObj = supplierResult;
            } else {
                resultObj = null;
            }
        } else {
            resultObj = (T) JsonUtil.json2Entity(result, clazz);

        }
        return resultObj;
    }


    /**
     * 如果值不存在则设置值，否知直接从缓存中读取
     *
     * @param key      the key
     * @param supplier the 读取数据的操作
     * @param timeOut  the 超时时间
     * @param timeUnit the 超时时间单位
     * @return 返回当前key的值。
     */
    public static <T> List<T> setIfNotExists(String key, Supplier<List<T>> supplier, Long timeOut, TimeUnit timeUnit, TypeReference<List<T>> t) {
        List<T> resultObj = null;
        String result = getValue(key);
        if (StringUtils.isEmpty(result)) {
            List<T> supplierResult = supplier.get();
            if (supplierResult != null) {
                setValueAndExpire(key, JsonUtil.entity2Json(supplierResult), timeUnit.toSeconds(timeOut));
                resultObj = supplierResult;
            } else {
                resultObj = null;
            }
        } else {
            resultObj = JsonUtil.json2Entity(result, t);

        }
        return resultObj;
    }

    /**
     * 判断可以是否存在
     *
     * @param key redis key
     * @return true 存在，false不存在
     */
    public static boolean exists(String key) {
        return Boolean.TRUE.equals(template.hasKey(key));
    }

    /**
     * 获取每个表的自增ID
     *
     * @param db    数据库名
     * @param table 表名
     * @return 自增的ID
     */
    public static long getID(String db, String table) {
        String key = KeyUtil.getKey("autoIncrementID", db, table);
        return value(key).increment(1);
    }

}
