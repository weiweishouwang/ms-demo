package com.zhw.ms.commons.config;

import com.mongodb.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * MongoConfig
 * Created by hongweizou on 16/12/20.
 */
@Configuration
@ConditionalOnProperty(name = "spring.data.mongodb.host", matchIfMissing = false)
public class MongodbConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongodbConfig.class);

    @Autowired
    private MongoProperties property;

    @Bean
    public MongoTemplate mongoTemplate() {
        LOGGER.info("---- get mongo template ----");
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("---- mongo host = {} ----", property.getHost());
            }
            MongoClientFactoryBean mongo = new MongoClientFactoryBean();
            mongo.setHost(property.getHost());
            mongo.setPort(property.getPort());
            ServerAddress[] addresses = createServerAddress();
            mongo.setReplicaSetSeeds(addresses);
            mongo.setSingleton(false);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("---- mongo address = {} ----", Arrays.toString(addresses));
            }
            MongoCredential credential = MongoCredential.createCredential(
                    property.getUsername(), property.getDatabase(), property.getPassword());
            mongo.setCredentials(new MongoCredential[]{credential});

            /*MongoClientOptions.Builder builder = MongoClientOptions.builder();
            builder.connectionsPerHost(10);
            builder.minConnectionsPerHost(5);
            builder.threadsAllowedToBlockForConnectionMultiplier(5);
            MongoClientOptions mongoClientOptions = builder.build();
            mongo.setMongoClientOptions(mongoClientOptions);*/

            MongoClient client = mongo.getObject();

            /*if (addresses == null) {
                client.setReadPreference(ReadPreference.secondaryPreferred());
            }*/

            //MongoClientOptions options = MongoClientOptions.builder().build();
            //MongoClient mongoClient = new MongoClient(addresses, credential, options);

            MongoTemplate template = new MongoTemplate(client, property.getDatabase());

            //DB db = template.getDb();
            //db.setReadPreference(ReadPreference.secondaryPreferred());

            return template;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ServerAddress[] createServerAddress() throws UnknownHostException {
        try {
            if (StringUtils.isBlank(property.getHost())) {
                return null;
            }

            List<ServerAddress> results = new ArrayList<>();

            String[] hosts = property.getHost().split(",");
            for (int i = 0; i < hosts.length; i++) {
                String[] arrays = hosts[i].split(":");
                results.add(new ServerAddress(arrays[0], Integer.parseInt(arrays[1])));
            }
            return results.toArray(new ServerAddress[0]);
        } catch (Exception e) {
            return null;
        }

    }

}
