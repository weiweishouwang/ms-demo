package com.zhw.ms.commons.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zhw.ms.commons.entity.Entity;
import com.zhw.ms.commons.filter.LogFilter;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * mongoDB操作共通
 * Query是查询的核心,可以实现自由查询,Query还可以指定offset,limit实现分页,还可以指定Sort排序
 * Update是更新的核心,可以实现任意字段的更新
 * TextQuery实现全文检索 Query query = TextQuery.searching(new TextCriteria().matchingAny("coffee", "cake")).sortByScore();
 * 详细文档:http://docs.spring.io/spring-data/data-mongo/docs/1.9.5.RELEASE/reference/html/#introduction
 * Created by hongweizou on 16/12/22.
 */
public class MongoBaseDaoImpl implements MongoBaseDao {
    private static final Logger logger = LoggerFactory.getLogger(MongoBaseDaoImpl.class);

    @Autowired
    protected MongoTemplate mongoTemplate;

    protected String dbName;

    @PostConstruct
    private void init() {
        dbName = mongoTemplate.getDb().getName();
    }

    @Override
    public IncrementID getID(String dbName, String collectionName) {
        String id = dbName + "_" + collectionName;

        // 取得nextID
        Query query = new Query(Criteria.where("collectionName").is(id));
        Update update = new Update().inc("nextID", 1);
        IncrementID incrementID = this.updateAndGetNew(query, update, IncrementID.class);

        // 插入主键数据
        if (incrementID == null) {
            incrementID = new IncrementID();
            incrementID.collectionName = id;
            incrementID.nextID = 1000000L;
            this.insert(incrementID);
            return this.updateAndGetNew(query, update, IncrementID.class);
        } else {
            return incrementID;
        }
    }

    private Object setObject(Object obj) {
        if (obj.getClass().equals(Object.class))
            throw new RuntimeException("use BaseDao.create method");

        if (obj instanceof Entity) {
            Entity entity = (Entity) obj;
            if (entity.created == null)
                entity.created = new Date();
            else if (entity.updated == null)
                entity.updated = new Date();

            if (entity.id == null) {
                String collectionName = mongoTemplate.getCollectionName(entity.getClass());
                entity.id = this.getID(dbName, collectionName).nextID;
            } else {
                entity.updated = new Date();
            }

            return entity;
        } else {
            return obj;
        }
    }

    private Object setVersion(Object obj) {
        if (obj.getClass().equals(Object.class))
            throw new RuntimeException("use BaseDao.create method");

        if (obj instanceof Entity) {
            Entity entity = (Entity) obj;
            entity.version = 0;
            return entity;
        } else {
            return obj;
        }
    }

    @Override
    public void insert(Object objectData) {
        setVersion(objectData);
        mongoTemplate.insert(setObject(objectData));
    }


    @Override
    public void insert(String collectionName, Object objectData) {
        setVersion(objectData);
        mongoTemplate.insert(setObject(objectData), collectionName);
    }


    @Override
    public <T extends Object> void insertBatch(Collection<? extends Object> objectDatas, Class<T> cls) {
        if (objectDatas == null) {
            return;
        }
        for (Object obj : objectDatas) {
            setVersion(obj);
            setObject(obj);
        }
        mongoTemplate.insert(objectDatas, cls);
    }


    @Override
    public void insertBatch(String collectionName, Collection<? extends Object> objectDatas) {
        if (objectDatas == null) {
            return;
        }
        for (Object obj : objectDatas) {
            setVersion(obj);
            setObject(obj);
        }
        mongoTemplate.insert(objectDatas, collectionName);
    }


    @Override
    public <T extends Object> void importData(Collection<? extends Object> objectDatas, Class<T> cls) {
        if (objectDatas == null) {
            return;
        }
        mongoTemplate.insert(objectDatas, cls);
    }


    @Override
    public void importData(String collectionName, Collection<? extends Object> objectDatas) {
        if (objectDatas == null) {
            return;
        }
        mongoTemplate.insert(objectDatas, collectionName);
    }


    @Override
    public void save(Object objectData) {
        mongoTemplate.save(setObject(objectData));
    }


    @Override
    public void save(String collectionName, Object objectData) {
        mongoTemplate.save(setObject(objectData), collectionName);
    }


    @Override
    public void insertAll(Collection<? extends Object> objectDatas) {
        if (objectDatas == null) {
            return;
        }
        for (Object obj : objectDatas) {
            setVersion(obj);
            setObject(obj);
        }
        mongoTemplate.insertAll(objectDatas);
    }


    @Override
    public <T extends Object> T get(Object id, Class<T> cls) {
        return mongoTemplate.findById(id, cls);
    }


    @Override
    public <T extends Object> T get(String collectionName, Object id, Class<T> cls) {
        return mongoTemplate.findById(id, cls, collectionName);
    }


    @Override
    public <T extends Object> List<T> findAll(Class<T> cls) {
        return mongoTemplate.findAll(cls);
    }


    @Override
    public <T extends Object> List<T> findAll(String collectionName, Class<T> cls) {
        return mongoTemplate.findAll(cls, collectionName);
    }


    @Override
    public <T extends Object> List<T> find(Query query, Class<T> cls) {
        return mongoTemplate.find(query, cls);
    }

    @Override
    public <T> List<T> find(Query query, Class<T> cls, String... includeFields) {
        Document fieldsObject = new Document();

        if (includeFields != null && includeFields.length > 0) {
            for (int i = 0; i < includeFields.length; i++) {
                fieldsObject.put(includeFields[i], true);
            }
        }

        BasicQuery basicQuery = new BasicQuery(query.getQueryObject(), fieldsObject);
        return mongoTemplate.find(basicQuery, cls);
    }

    @Override
    public <T extends Object> List<T> find(String collectionName, Query query, Class<T> cls) {
        return mongoTemplate.find(query, cls, collectionName);
    }

    @Override
    public <T extends Object> List<T> findByIds(Collection<? extends Object> ids, Class<T> cls) {
        Criteria criteria = Criteria.where("id").in(ids);
        Query query = new Query(criteria);
        query.with(new Sort(Sort.Direction.DESC, "id"));
        return mongoTemplate.find(query, cls);
    }

    @Override
    public <T extends Object> List<T> findByIds(String collectionName, Collection<? extends Object> ids, Class<T> cls) {
        Criteria criteria = Criteria.where("id").in(ids);
        Query query = new Query(criteria);
        query.with(new Sort(Sort.Direction.DESC, "id"));
        return mongoTemplate.find(query, cls, collectionName);
    }

    @Override
    public <T extends Object> T findOne(Query query, Class<T> cls) {
        query.skip(0).limit(1);
        return mongoTemplate.findOne(query, cls);
    }

    @Override
    public <T extends Object> T findOne(String collectionName, Query query, Class<T> cls) {
        query.skip(0).limit(1);
        return mongoTemplate.findOne(query, cls, collectionName);
    }

    @Override
    public boolean remove(Object objectData) {
        DeleteResult result = mongoTemplate.remove(objectData);
        logger.info(result.toString());
        return result.wasAcknowledged();
    }

    @Override
    public boolean remove(String collectionName, Object objectData) {
        DeleteResult result = mongoTemplate.remove(objectData, collectionName);
        logger.info(result.toString());
        return result.wasAcknowledged();
    }

    @Override
    public boolean remove(Collection<? extends Object> objectDatas) {
        if (objectDatas == null) {
            return false;
        }
        for (Object objectData : objectDatas) {
            remove(objectData);
        }
        return true;
    }

    @Override
    public boolean remove(String collectionName, Collection<? extends Object> objectDatas) {
        if (objectDatas == null) {
            return false;
        }
        for (Object objectData : objectDatas) {
            remove(collectionName, objectData);
        }
        return true;
    }

    @Override
    public <T extends Object> boolean removeById(Class<T> cls, Object id) {
        return removeByIds(Collections.singletonList(id), cls);
    }


    @Override
    public boolean removeById(String collectionName, Object id) {
        return removeByIds(collectionName, Collections.singletonList(id));
    }

    @Override
    public <T extends Object> boolean removeByIds(Collection<? extends Object> ids, Class<T> cls) {
        Criteria criteria = Criteria.where("id").in(ids);
        Query query = new Query(criteria);
        DeleteResult result = mongoTemplate.remove(query, cls);
        logger.info(result.toString());
        return result.wasAcknowledged();
    }

    @Override
    public <T extends Object> boolean removeByIds(String collectionName, Collection<? extends Object> ids, Class<T> cls) {
        Criteria criteria = Criteria.where("id").in(ids);
        Query query = new Query(criteria);
        DeleteResult result = mongoTemplate.remove(query, cls, collectionName);
        logger.info(result.toString());
        return result.wasAcknowledged();
    }

    @Override
    public boolean removeByIds(String collectionName, Collection<? extends Object> ids) {
        Criteria criteria = Criteria.where("id").in(ids);
        Query query = new Query(criteria);
        DeleteResult result = mongoTemplate.remove(query, collectionName);
        logger.info(result.toString());
        return result.wasAcknowledged();
    }

    @Override
    public <T extends Object> List<T> removeAndGet(Query query, Class<T> cls) {
        return mongoTemplate.findAllAndRemove(query, cls);
    }

    @Override
    public <T extends Object> List<T> removeAndGet(String collectionName, Query query, Class<T> cls) {
        return mongoTemplate.findAllAndRemove(query, cls, collectionName);
    }


    @Override
    public <T extends Object> List<T> removeAndGet(String collectionName, Query query) {
        return mongoTemplate.findAllAndRemove(query, collectionName);
    }


    @Override
    public <T extends Object> boolean remove(Query query, Class<T> cls) {
        DeleteResult result = mongoTemplate.remove(query, cls);
        logger.info(result.toString());
        return result.wasAcknowledged();
    }


    @Override
    public <T extends Object> boolean remove(String collectionName, Query query, Class<T> cls) {
        DeleteResult result = mongoTemplate.remove(query, cls, collectionName);
        logger.info(result.toString());
        return result.wasAcknowledged();
    }


    @Override
    public boolean remove(String collectionName, Query query) {
        DeleteResult result = mongoTemplate.remove(query, collectionName);
        logger.info(result.toString());
        return result.wasAcknowledged();
    }

    @Override
    public void replace(Object objectData) {
        save(objectData);
    }


    public void replace(String collectionName, Object objectData) {
        save(collectionName, objectData);
    }

    @Override
    public void replaceAll(Collection<? extends Object> objectDatas) {
        if (objectDatas == null) {
            return;
        }
        for (Object objectData : objectDatas) {
            save(objectData);
        }
    }

    @Override
    public void replaceAll(String collectionName, Collection<? extends Object> objectDatas) {
        if (objectDatas == null) {
            return;
        }
        for (Object objectData : objectDatas) {
            save(collectionName, objectData);
        }
    }

    @Override
    public <T extends Object> boolean update(Query query, Update update, Class<T> cls) {
        UpdateResult result = mongoTemplate.updateMulti(query, update, cls);
        logger.info(result.toString());
        return result.wasAcknowledged();
    }

    @Override
    public <T extends Object> boolean update(String collectionName, Query query, Update update, Class<T> cls) {
        UpdateResult result = mongoTemplate.updateMulti(query, update, cls, collectionName);
        logger.info(result.toString());
        return result.wasAcknowledged();
    }

    @Override
    public boolean update(String collectionName, Query query, Update update) {
        UpdateResult result = mongoTemplate.updateMulti(query, update, collectionName);
        logger.info(result.toString());
        return result.wasAcknowledged();
    }

    @Override
    public <T extends Object> T updateAndGetOld(Query query, Update update, Class<T> cls) {
        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(false), cls);
    }

    @Override
    public <T extends Object> T updateAndGetOld(String collectionName, Query query, Update update, Class<T> cls) {
        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(false), cls, collectionName);
    }

    @Override
    public <T extends Object> T updateAndGetNew(Query query, Update update, Class<T> cls) {
        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), cls);
    }

    @Override
    public <T extends Object> T updateAndGetNew(String collectionName, Query query, Update update, Class<T> cls) {
        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), cls, collectionName);
    }

    @Override
    public <T extends Object> boolean upsert(Query query, Update update, Class<T> cls) {
        UpdateResult result = mongoTemplate.upsert(query, update, cls);
        logger.info(result.toString());
        return result.wasAcknowledged();
    }

    @Override
    public <T extends Object> boolean upsert(String collectionName, Query query, Update update, Class<T> cls) {
        UpdateResult result = mongoTemplate.upsert(query, update, cls, collectionName);
        logger.info(result.toString());
        return result.wasAcknowledged();
    }

    @Override
    public boolean upsert(String collectionName, Query query, Update update) {
        UpdateResult result = mongoTemplate.upsert(query, update, collectionName);
        logger.info(result.toString());
        return result.wasAcknowledged();
    }

    @Override
    public <T extends Object> long count(Query query, Class<T> cls) {
        return mongoTemplate.count(query, cls);
    }

    @Override
    public <T extends Object> long count(String collectionName, Query query, Class<T> cls) {
        return mongoTemplate.count(query, cls, collectionName);
    }

    @Override
    public long count(String collectionName, Query query) {
        return mongoTemplate.count(query, collectionName);
    }

    @Override
    public <T extends Object> long count(Class<T> cls) {
        return mongoTemplate.count(new Query(), cls);
    }

    @Override
    public <T extends Object> long count(String collectionName, Class<T> cls) {
        return mongoTemplate.count(new Query(), cls, collectionName);
    }

    @Override
    public <T extends Object> void drop(Class<T> cls) {
        mongoTemplate.dropCollection(cls);
    }

    @Override
    public void drop(String collectionName) {
        mongoTemplate.dropCollection(collectionName);
    }

    @Override
    public <T extends Object> boolean exists(Query query, Class<T> cls) {
        return mongoTemplate.exists(query, cls);
    }

    @Override
    public <T extends Object> boolean exists(String collectionName, Query query, Class<T> cls) {
        return mongoTemplate.exists(query, cls, collectionName);
    }

    @Override
    public boolean exists(String collectionName, Query query) {
        return mongoTemplate.exists(query, collectionName);
    }

}
