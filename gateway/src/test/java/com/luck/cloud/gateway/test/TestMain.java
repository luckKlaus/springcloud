package com.luck.cloud.gateway.test;

import com.luck.cloud.gateway.dao.UserTestDao;
import com.luck.cloud.gateway.redis.IRedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by luck on 2017/12/29.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMain {


    @Autowired
    IRedisService redisService;

    @Autowired
    UserTestDao userTestDao;


    @Test
    public void testGateWayRedis(){

        System.out.println(redisService.get("testRedisKey"));
    }

    @Test
    public void testDB(){
        System.out.println(userTestDao.selectAll());
        System.out.println("==============================");
        System.out.println(userTestDao.queryUserTestResultSql());
    }

}
