package com.luck.cloud.gateway.entity;

/**
 * Created by luck on 2017/12/28.
 */
public class GateWayEntity {

    /** id */
    private Integer id;
    /** userName */
    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "GateWayEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                '}';
    }
}
