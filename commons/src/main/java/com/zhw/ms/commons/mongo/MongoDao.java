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
 *
 * @param <O> 处理的数据类型
 */
public interface MongoDao<O extends Object> extends MongoBaseDao {

    /**
     * 批量插入
     *
     * @param objectDatas 需要处理的数据
     */
    public void insertBatch(Collection<? extends Object> objectDatas);

    /**
     * 批量插入
     *
     * @param objectDatas 需要处理的数据
     */
    public void importData(Collection<? extends Object> objectDatas);

    /**
     * 根据id查找
     *
     * @param id id
     * @return 目标数据
     */
    public O getBean(Object id);

    /**
     * 根据id查找
     *
     * @param collectionName collectionName
     * @param id             id
     * @return 目标数据
     */
    public O getBean(String collectionName, Object id);

    /**
     * 查找全部
     *
     * @return 目标数据
     */
    public List<O> findAll();

    /**
     * 查找全部
     *
     * @param collectionName collectionName
     * @return 目标数据
     */
    public List<O> findAll(String collectionName);

    /**
     * 根据条件查找全部
     *
     * @param query 查询条件
     * @return 目标数据
     */
    public List<O> find(Query query);

    /**
     * 根据条件查找全部
     *
     * @param query         查询条件
     * @param includeFields 被查询的字段
     * @return 目标数据
     */
    public List<O> find(Query query, String... includeFields);

    /**
     * 根据条件查找全部
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @return 目标数据
     */
    public List<O> find(String collectionName, Query query);

    /**
     * 根据条件查找全部
     *
     * @param ids id集合
     * @return 目标数据
     */
    public List<O> findByIds(Collection<? extends Object> ids);

    /**
     * 根据条件查找全部
     *
     * @param collectionName collectionName
     * @param ids            id结合
     * @return 目标数据
     */
    public List<O> findByIds(String collectionName, Collection<? extends Object> ids);

    /**
     * 根据条件查找全部
     *
     * @param query 查询条件
     * @return 目标数据
     */
    public O findOne(Query query);

    /**
     * 根据条件查找全部
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @return 目标数据
     */
    public O findOne(String collectionName, Query query);

    /**
     * 根据条件删除
     *
     * @param query 查询条件
     * @return list
     */
    public List<O> removeAndGet(Query query);

    /**
     * 根据条件删除
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @return List
     */
    public List<O> removeAndGetBean(String collectionName, Query query);

    /**
     * 根据条件删除
     *
     * @param query 查询条件
     * @return boolean
     */
    public boolean remove(Query query);

    /**
     * 根据条件删除
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @return boolean
     */
    public boolean removeBean(String collectionName, Query query);


    /**
     * 根绝id删除一行
     *
     * @param id id
     * @return boolean
     */
    public boolean removeById(Object id);

    /**
     * 根绝id删除
     *
     * @param ids id集合
     * @return boolean
     */
    public boolean removeByIds(Collection<? extends Object> ids);

    /**
     * 根据id删除
     *
     * @param collectionName collectionName
     * @param ids            id集合
     * @return boolean
     */
    public boolean removeBeanByIds(String collectionName, Collection<? extends Object> ids);

    /**
     * 按条件更新制定的数据
     *
     * @param query  查询条件
     * @param update Update对象
     * @return boolean
     */
    public boolean update(Query query, Update update);

    /**
     * 按条件更新制定的数据
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @param update         Update对象
     * @return boolean
     */
    public boolean updateBean(String collectionName, Query query, Update update);

    /**
     * 更新并返回之前的数据
     *
     * @param query  查询条件
     * @param update Update对象
     * @return 目标数据
     */
    public O updateAndGetOld(Query query, Update update);

    /**
     * 更新并返回之前的数据
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @param update         Update对象
     * @return 目标数据
     */
    public O updateAndGetOld(String collectionName, Query query, Update update);

    /**
     * 更新并返回之后的数据
     *
     * @param query  查询条件
     * @param update Update对象
     * @return 目标数据
     */
    public O updateAndGetNew(Query query, Update update);

    /**
     * 更新并返回之后的数据
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @param update         Update对象
     * @return 目标数据
     */
    public O updateAndGetNew(String collectionName, Query query, Update update);

    /**
     * 更新,如果列不存在就增加列
     *
     * @param query  查询条件
     * @param update Update对象
     * @return boolean
     */
    public boolean upsert(Query query, Update update);

    /**
     * 更新,如果列不存在就增加列
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @param update         Update对象
     * @return boolean
     */
    public boolean upsertBean(String collectionName, Query query, Update update);

    /**
     * 查询数量
     *
     * @param query 查询条件
     * @return long
     */
    public long count(Query query);

    /**
     * 查询数量
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @return long
     */
    public long countBean(String collectionName, Query query);


    /**
     * 查询数量
     *
     * @return long
     */
    public long count();

    /**
     * 查询数量
     *
     * @param collectionName collectionName
     * @return long
     */
    public long count(String collectionName);

    /**
     * 删除集合
     */
    public void drop();

    /**
     * 是否存在
     *
     * @param query 查询条件
     * @return boolean
     */
    public boolean exists(Query query);

    /**
     * 是否存在
     *
     * @param collectionName collectionName
     * @param query          查询条件
     * @return boolean
     */
    public boolean existsBean(String collectionName, Query query);

}
