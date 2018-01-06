package com.luck.cloud.gateway.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luck.cloud.gateway.dao.UserTestDao;
import com.luck.cloud.gateway.entity.UserTest;
import com.luck.cloud.gateway.redis.IRedisService;
import com.luck.common.entity.PageResult;
import com.luck.common.utils.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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
        PageHelper.startPage(1, 3);
        PageInfo<UserTest> pageinfo = new PageInfo<UserTest>(userTestDao.queryUserTestResult(new UserTest()));
        PageResult<UserTest> pageResult = new PageResult<UserTest>(pageinfo);
        System.out.println(JsonUtil.objectToJson(pageResult));
//        System.out.println(userTestDao.selectAll());
        //测试分页


//        System.out.println("==============================");
//        System.out.println(userTestDao.queryUserTestResultSql());
//        System.out.println("========================");
//        System.out.println(userTestDao.queryUserTestResult(new UserTest()));
    }

}
