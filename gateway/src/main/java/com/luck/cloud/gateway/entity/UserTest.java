package com.luck.cloud.gateway.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by luck on 2017/09/28.
 */
@Table(name = "user_test")
public class UserTest {
	
    /**用户名*/
    @Column(name = "userName")
    private String userName;
    /**年龄*/
    private Integer age;
    /**密码*/
    private String passWord;

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

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
