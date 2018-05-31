package com.zhw.ms.demo.rest;

import com.zhw.ms.demo.entity.Admin;
import com.zhw.ms.demo.mongo.dao.AdminMongoDao;
import com.zhw.ms.demo.service.DemoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hongweizou on 16/9/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoRestTest {
    @Autowired
    private DemoRest demoRest;

    @Autowired
    private AdminMongoDao adminMongoDao;

    @Autowired
    private DemoService demoService;

    @Test
    public void test01() {
        Assert.assertTrue(demoRest.getAdmin(1L).isSuccess());
    }

    @Test
    public void test02() {
        Admin admin = demoService.getAdmin(1L);
        Assert.assertTrue(demoRest.getAdmin(1L).isSuccess());
        admin.setId(null);
        adminMongoDao.insert(admin);
    }
}
