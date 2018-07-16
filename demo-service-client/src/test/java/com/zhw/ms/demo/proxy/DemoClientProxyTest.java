package com.zhw.ms.demo.proxy;

import com.zhw.ms.common.contract.bean.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by hongweizou on 16/9/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoClientProxyTest {
    @Autowired
    private DemoClientProxy demoClientProxy;

    @Test
    public void test01() {
        Assert.assertTrue(demoClientProxy.getAdmin(1L).isSuccess());
    }

    @Test
    public void test02() throws ExecutionException, InterruptedException {
        Future<Result<Object>> future = demoClientProxy.getAdminAsync(1L);
        Assert.assertTrue(future.get().isSuccess());
    }
}
