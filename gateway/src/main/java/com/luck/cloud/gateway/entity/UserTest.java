package com.luck.cloud.gateway.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by luck on 2017/09/28.
 */
//@Table(name = "user_test")
public class UserTest {
	
    /**用户名*/
    @Column(name = "userName")
    @NotNull
    private String userName;
    /**年龄*/
    @NotNull(message = "年龄不能为空")
    private Integer age;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserTest{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
