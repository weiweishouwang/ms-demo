package com.zhw.ms.commons;

import com.zhw.ms.commons.cache.RedisUtil;
import com.zhw.ms.commons.dao.AdminMongoDao;
import com.zhw.ms.commons.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import com.zhw.ms.commons.utils.DateUtil;

/**
 * Created by Administrator on 2015/12/31 0031.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicTest {

    @Autowired
    private AdminMongoDao adminMongoDao;

    @Test
    public void test01() {
        System.out.println(DateUtil.format(new Date(), DateUtil.YYYYMMDDHHMMSSMS));
    }

    @Test
    public void test02() {
        RedisUtil.setValue("test", "test", 1);
        System.out.println(RedisUtil.getValue("test"));
    }

    @Test
    public void test03() {
        Admin admin = new Admin();
        admin.setAccount("admin");
        adminMongoDao.insert(admin);
        System.out.println(adminMongoDao.getBean(admin.id).getAccount());
    }

}
