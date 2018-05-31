package com.zhw.ms.commons.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
@Component
public class MongoDaoImpl<O extends Object> extends MongoBaseDaoImpl implements MongoDao<O> {
    private static final Logger logger = LoggerFactory.getLogger(MongoDaoImpl.class);

    protected Class<O> objClass = null;//(Class<O>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public MongoDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            objClass = (Class<O>) p[0];
        }
    }

    @PostConstruct
    private void init() {
        dbName = mongoTemplate.getDb().getName();
    }

    @Override
    public void insertBatch(Collection<?> objectDatas) {
        insertBatch(objectDatas, objClass);
    }

    @Override
    public void importData(Collection<?> objectDatas) {
        importData(objectDatas, objClass);
    }

    @Override
    public O getBean(Object id) {
        return get(id, objClass);
    }

    @Override
    public O getBean(String collectionName, Object id) {
        return get(collectionName, id, objClass);
    }

    @Override
    public List<O> findAll() {
        return findAll(objClass);
    }

    @Override
    public List<O> findAll(String collectionName) {
        return findAll(collectionName, objClass);
    }

    @Override
    public List<O> find(Query query) {
        return find(query, objClass);
    }

    @Override
    public List<O> find(Query query, String... includeFields) {
        return find(query, objClass, includeFields);
    }

    @Override
    public List<O> find(String collectionName, Query query) {
        return find(collectionName, query, objClass);
    }

    @Override
    public List<O> findByIds(Collection<? extends Object> ids) {
        return findByIds(ids, objClass);
    }

    @Override
    public List<O> findByIds(String collectionName, Collection<? extends Object> ids) {
        return findByIds(collectionName, ids, objClass);
    }

    @Override
    public O findOne(Query query) {
        return findOne(query, objClass);
    }

    @Override
    public O findOne(String collectionName, Query query) {
        return findOne(collectionName, query, objClass);
    }

    @Override
    public List<O> removeAndGet(Query query) {
        return removeAndGet(query, objClass);
    }

    @Override
    public List<O> removeAndGetBean(String collectionName, Query query) {
        return removeAndGet(collectionName, query, objClass);
    }

    @Override
    public boolean remove(Query query) {
        return remove(query, objClass);
    }

    @Override
    public boolean removeBean(String collectionName, Query query) {
        return remove(collectionName, query, objClass);
    }

    @Override
    public boolean removeById(Object id) {
        return removeById(objClass, id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Object> ids) {
        return removeByIds(ids, objClass);
    }

    @Override
    public boolean removeBeanByIds(String collectionName, Collection<? extends Object> ids) {
        return removeByIds(collectionName, ids, objClass);
    }

    @Override
    public boolean update(Query query, Update update) {
        return update(query, update, objClass);
    }

    @Override
    public boolean updateBean(String collectionName, Query query, Update update) {
        return update(collectionName, query, update, objClass);
    }

    @Override
    public O updateAndGetOld(Query query, Update update) {
        return updateAndGetOld(query, update, objClass);
    }

    @Override
    public O updateAndGetOld(String collectionName, Query query, Update update) {
        return updateAndGetOld(collectionName, query, update, objClass);
    }

    @Override
    public O updateAndGetNew(Query query, Update update) {
        return updateAndGetNew(query, update, objClass);
    }

    @Override
    public O updateAndGetNew(String collectionName, Query query, Update update) {
        return updateAndGetNew(collectionName, query, update, objClass);
    }

    @Override
    public boolean upsert(Query query, Update update) {
        return upsert(query, update, objClass);
    }

    @Override
    public boolean upsertBean(String collectionName, Query query, Update update) {
        return upsert(collectionName, query, update, objClass);
    }

    @Override
    public long count(Query query) {
        return count(query, objClass);
    }

    @Override
    public long countBean(String collectionName, Query query) {
        return count(collectionName, query, objClass);
    }

    @Override
    public long count() {
        return count(objClass);
    }

    @Override
    public long count(String collectionName) {
        return count(collectionName, objClass);
    }

    @Override
    public void drop() {
        drop(objClass);
    }

    @Override
    public boolean exists(Query query) {
        return exists(query, objClass);
    }

    @Override
    public boolean existsBean(String collectionName, Query query) {
        return exists(collectionName, query, objClass);
    }

    /*@Override
    public void insert(Object objectData) {
        if (!objectData.getClass().getName().equals(objClass.getName())) {
            throw new RuntimeException("dao和entity不匹配！");
        }
        super.insert(objectData);
    }

    @Override
    public void insertAll(Collection<? extends Object> objectDatas) {
        if (objectDatas == null) {
            return;
        }

        String className = objClass.getName();
        for (Object obj : objectDatas) {
            if (!obj.getClass().getName().equals(className)) {
                throw new RuntimeException("dao和entity不匹配！");
            }
        }

        super.insertAll(objectDatas);
    }


    @Override
    public void save(Object objectData) {
        if (!objectData.getClass().getName().equals(objClass.getName())) {
            throw new RuntimeException("dao和entity不匹配！");
        }
        super.save(objectData);
    }*/

}
