package com.zhw.ms.commons.mongo;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collection;
import java.util.List;

/**
 * mongoDB操作共通
 * Query是查询的核心,可以实现自由查询,Query还可以指定offset,limit实现分页,还可以指定Sort排序
 * Update是更新的核心,可以实现任意字段的更新
 * TextQuery实现全文检索 Query query = TextQuery.searching(new TextCriteria().matchingAny("coffee", "cake")).sortByScore();
 * 详细文档:http://docs.spring.io/spring-data/data-mongo/docs/1.9.5.RELEASE/reference/html/#introduction
 * Created by hongweizou on 16/12/22.
 */
public interface MongoBaseDao {

    public IncrementID getID(String dbName, String collectionName);

    /**
     * 插入一行
     *
     * @param objectData objectData
     */
    public void insert(Object objectData);

    /**
     * 插入一行
     *
     * @param collectionName collectionName
     * @param objectData     objectData
     */
    public void insert(String collectionName, Object objectData);

    /**
     * 批量插入
     *
     * @param objectDatas objectDatas
     * @param cls         Class
     * @param <T>         处理数据类型
     */
    public <T extends Object> void insertBatch(Collection<? extends Object> objectDatas, Class<T> cls);

    /**
     * 批量插入
     *
     * @param collectionName collectionName
     * @param objectDatas    objectDatas
     */
    public void insertBatch(String collectionName, Collection<? extends Object> objectDatas);

    /**
     * 批量插入
     *
     * @param objectDatas objectDatas
     * @param cls         Class
     * @param <T>         处理数据类型
     */
    public <T extends Object> void importData(Collection<? extends Object> objectDatas, Class<T> cls);

    /**
     * 批量插入
     *
     * @param collectionName collectionName
     * @param objectDatas    objectDatas
     */
    public void importData(String collectionName, Collection<? extends Object> objectDatas);

    /**
     * 插入一行或按id覆盖
     *
     * @param objectData objectData
     */
    public void save(Object objectData);

    /**
     * 插入一行或按id覆盖
     *
     * @param collectionName collectionName
     * @param objectData     objectData
     */
    public void save(String collectionName, Object objectData);

    /**
     * 批量插入
     *
     * @param objectDatas objectDatas
     */
    public void insertAll(Collection<? extends Object> objectDatas);

    /**
     * 根据id查找
     *
     * @param id  id
     * @param cls Class
     * @param <T> 处理数据类型
     * @return 目标数据
     */
    public <T extends Object> T get(Object id, Class<T> cls);

    /**
     * 根据id查找
     *
     * @param collectionName collectionName
     * @param id             id
     * @param cls            Class
     * @param <T>            处理数据类型
     * @return 目标数据
     */
    public <T extends Object> T get(String collectionName, Object id, Class<T> cls);

    /**
     * 查找全部
     *
     * @param cls Class
     * @param <T> 处理数据类型
     * @return 目标数据
     */
    public <T extends Object> List<T> findAll(Class<T> cls);

    /**
     * 查找全部
     *
     * @param collectionName collectionName
     * @param cls            Class
     * @param <T>            处理数据类型
     * @return 目标数据
     */
    public <T extends Object> List<T> findAll(String collectionName, Class<T> cls);

    /**
     * 根据条件查找全部
     *
     * @param cls   Class
     * @param query 查询条件
     * @param <T>   处理数据类型
     * @return 目标数据
     */
    public <T extends Object> List<T> find(Query query, Class<T> cls);

    /**
     * 根据条件查找全部
     *
     * @param cls           Class
     * @param query         查询条件
     * @param includeFields 被查询的字段
     * @param <T>           处理数据类型
     * @return 目标数据
     */
    public <T extends Object> List<T> find(Query query, Class<T> cls, String... includeFields);

    /**
     * 根据条件查找全部
     *
     * @param collectionName collectionName
     * @param cls            Class
     * @param query          查询条件
     * @param <T>            处理数据类型
     * @return 目标数据
     */
    public <T extends Object> List<T> find(String collectionName, Query query, Class<T> cls);

    /**
     * 根据条件查找全部
     *
     * @param cls Class
     * @param ids id集合
     * @param <T> 处理数据类型
     * @return 目标数据
     */
    public <T extends Object> List<T> findByIds(Collection<? extends Object> ids, Class<T> cls);

    /**
     * 根据条件查找全部
     *
     * @param collectionName collectionName
     * @param cls            Class
     * @param ids            id集合
     * @param <T>            处理数据类型
     * @return 目标数据
     */
    public <T extends Object> List<T> findByIds(String collectionName, Collection<? extends Object> ids, Class<T> cls);

    /**
     * 根据条件查找全部
     *
     * @param cls   Class
     * @param query 查询条件
     * @param <T>   处理数据类型
     * @return 目标数据
     */
    public <T extends Object> T findOne(Query query, Class<T> cls);

    /**
     * 根据条件查找全部
     *
     * @param collectionName collectionName
     * @param cls            Class
     * @param query          查询条件
     * @param <T>            处理数据类型
     * @return 目标数据
     */
    public <T extends Object> T findOne(String collectionName, Query query, Class<T> cls);

    /**
     * 删除一行
     *
     * @param objectData objectData
     * @return boolean
     */
    public boolean remove(Object objectData);

    /**
     * 删除一行
     *
     * @param collectionName collectionName
     * @param objectData     objectData
     * @return boolean
     */
    public boolean remove(String collectionName, Object objectData);

    /**
     * 批量删除
     *
     * @param objectDatas objectDatas
     * @return boolean
     */
    public boolean remove(Collection<? extends Object> objectDatas);

    /**
     * 批量删除
     *
     * @param collectionName collectionName
     * @param objectDatas    objectData
     * @return boolean
     */
    public boolean remove(String collectionName, Collection<? extends Object> objectDatas);

    /**
     * 根绝id删除一行
     *
     * @param id  id
     * @param <T> 处理数据类型
     * @param cls Class
     * @return 目标数据
     */
    public <T extends Object> boolean removeById(Class<T> cls, Object id);

    /**
     * 根据id删除一行
     *
     * @param collectionName collectionName
     * @param id             id
     * @return 目标数据
     */
    public boolean removeById(String collectionName, Object id);

    /**
     * 根绝id删除
     *
     * @param ids id集合
     * @param <T> 处理数据类型
     * @param cls Class
     * @return 目标数据
     */
    public <T extends Object> boolean removeByIds(Collection<? extends Object> ids, Class<T> cls);

    /**
     * 根据id删除
     *
     * @param collectionName collectionName
     * @param ids            id集合
     * @param <T>            处理数据类型
     * @param cls            Class
     * @return 目标数据
     */
    public <T extends Object> boolean removeByIds(String collectionName, Collection<? extends Object> ids, Class<T> cls);

    /**
     * 根据id删除
     *
     * @param collectionName collectionName
     * @param ids            id集合
     * @return 目标数据
     */
    public boolean removeByIds(String collectionName, Collection<? extends Object> ids);

    /**
     * 根据条件删除
     *
     * @param query 查询条件
     * @param cls   Class
     * @param <T>   处理数据类型
     * @return 目标数据
     */
    public <T extends Object> List<T> removeAndGet(Query query, Class<T> cls);

    /**
     * 根据条件删除
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @param cls            Class
     * @param <T>            处理数据类型
     * @return 目标数据
     */
    public <T extends Object> List<T> removeAndGet(String collectionName, Query query, Class<T> cls);

    /**
     * 根据条件删除
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @param <T>            处理数据类型
     * @return 目标数据
     */
    public <T extends Object> List<T> removeAndGet(String collectionName, Query query);

    /**
     * 根据条件删除
     *
     * @param query 查询条件
     * @param cls   Class
     * @param <T>   处理数据类型
     * @return 目标数据
     */
    public <T extends Object> boolean remove(Query query, Class<T> cls);

    /**
     * 根据条件删除
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @param cls            Class
     * @param <T>            处理数据类型
     * @return 目标数据
     */
    public <T extends Object> boolean remove(String collectionName, Query query, Class<T> cls);

    /**
     * 根据条件删除
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @return 目标数据
     */
    public boolean remove(String collectionName, Query query);


    /**
     * 按id覆盖一行
     *
     * @param objectData objectData
     */
    public void replace(Object objectData);

    /**
     * 按id覆盖一行
     *
     * @param collectionName collectionName
     * @param objectData     objectData
     */
    public void replace(String collectionName, Object objectData);

    /**
     * 按id批量覆盖
     *
     * @param objectDatas objectDatas
     */
    public void replaceAll(Collection<? extends Object> objectDatas);

    /**
     * 按id批量覆盖
     *
     * @param collectionName collectionName
     * @param objectDatas    objectDatas
     */
    public void replaceAll(String collectionName, Collection<? extends Object> objectDatas);

    /**
     * 按条件更新制定的数据
     *
     * @param query  查询条件
     * @param update Update
     * @param cls    Class
     * @param <T>    处理数据类型
     * @return 目标数据
     */
    public <T extends Object> boolean update(Query query, Update update, Class<T> cls);

    /**
     * 按条件更新制定的数据
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @param update         Update
     * @param cls            Class
     * @param <T>            处理数据类型
     * @return 目标数据
     */
    public <T extends Object> boolean update(String collectionName, Query query, Update update, Class<T> cls);

    /**
     * 按条件更新制定的数据
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @param update         Update
     * @return 目标数据
     */
    public boolean update(String collectionName, Query query, Update update);

    /**
     * 更新并返回之前的数据
     *
     * @param query  查询条件
     * @param update Update
     * @param cls    Class
     * @param <T>    处理数据类型
     * @return 目标数据
     */
    public <T extends Object> T updateAndGetOld(Query query, Update update, Class<T> cls);

    /**
     * 更新并返回之前的数据
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @param update         Update
     * @param cls            Class
     * @param <T>            处理数据类型
     * @return 目标数据
     */
    public <T extends Object> T updateAndGetOld(String collectionName, Query query, Update update, Class<T> cls);

    /**
     * 更新并返回之后的数据
     *
     * @param query  查询条件
     * @param update Update
     * @param cls    Class
     * @param <T>    处理数据类型
     * @return 目标数据
     */
    public <T extends Object> T updateAndGetNew(Query query, Update update, Class<T> cls);

    /**
     * 更新并返回之后的数据
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @param update         Update
     * @param cls            Class
     * @param <T>            处理数据类型
     * @return 目标数据
     */
    public <T extends Object> T updateAndGetNew(String collectionName, Query query, Update update, Class<T> cls);

    /**
     * 更新,如果列不存在就增加列
     *
     * @param query  查询条件
     * @param update Update
     * @param cls    Class
     * @param <T>    处理数据类型
     * @return 目标数据
     */
    public <T extends Object> boolean upsert(Query query, Update update, Class<T> cls);

    /**
     * 更新,如果列不存在就增加列
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @param update         Update
     * @param cls            Class
     * @param <T>            处理数据类型
     * @return 目标数据
     */
    public <T extends Object> boolean upsert(String collectionName, Query query, Update update, Class<T> cls);

    /**
     * 更新,如果列不存在就增加列
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @param update         Update
     * @return 目标数据
     */
    public boolean upsert(String collectionName, Query query, Update update);

    /**
     * 查询数量
     *
     * @param query 查询条件
     * @param cls   Class
     * @param <T>   处理数据类型
     * @return 目标数据
     */
    public <T extends Object> long count(Query query, Class<T> cls);

    /**
     * 查询数量
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @param cls            Class
     * @param <T>            处理数据类型
     * @return 目标数据
     */
    public <T extends Object> long count(String collectionName, Query query, Class<T> cls);

    /**
     * 查询数量
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @return 目标数据
     */
    public long count(String collectionName, Query query);

    /**
     * 查询数量
     *
     * @param cls Class
     * @param <T> 处理数据类型
     * @return 目标数据
     */
    public <T extends Object> long count(Class<T> cls);

    /**
     * 查询数量
     *
     * @param collectionName collectionName
     * @param cls            Class
     * @param <T>            处理数据类型
     * @return 目标数据
     */
    public <T extends Object> long count(String collectionName, Class<T> cls);

    /**
     * 删除集合
     *
     * @param cls Class
     * @param <T> 处理数据类型
     */
    public <T extends Object> void drop(Class<T> cls);

    /**
     * 删除集合
     *
     * @param collectionName collectionName
     */
    public void drop(String collectionName);

    /**
     * 是否存在
     *
     * @param query 查询条件
     * @param cls   Class
     * @param <T>   处理数据类型
     * @return 目标数据
     */
    public <T extends Object> boolean exists(Query query, Class<T> cls);

    /**
     * 是否存在
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @param cls            Class
     * @param <T>            数据类型
     * @return 目标数据
     */
    public <T extends Object> boolean exists(String collectionName, Query query, Class<T> cls);

    /**
     * 是否存在
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @return 目标数据
     */
    public boolean exists(String collectionName, Query query);
}
