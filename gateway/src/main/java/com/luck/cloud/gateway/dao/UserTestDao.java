package com.luck.cloud.gateway.dao;

import com.luck.cloud.gateway.entity.UserTest;
import com.luck.common.mybatis.MyMapper
        ;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by luck on 2017/09/28.
 */
public interface UserTestDao extends MyMapper<UserTest> {



    List<UserTest> queryUserTestResult(UserTest record);

    @Select("SELECT * FROM  user_test")
    List<UserTest> queryUserTestResultSql();
}
