package com.zhw.ms.demo.client;

import com.zhw.ms.demo.DemoClientApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hongweizou on 16/9/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoClientTest {
    @Autowired
    private DemoClient demoClient;

    @Test
    public void test01() {
        Assert.assertTrue(demoClient.getAdmin(1L).isSuccess());
    }
}
