package com.linxz.doexcel.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

public class User {
    @ExcelIgnore
    private Long user_id;

    @ExcelProperty(value = "组员姓名", index = 0)
    private String user_name;

    @ExcelProperty(value = "团队名称", index = 1)
    private String user_team;

    @ExcelIgnore
    private Long user_money;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_team() {
        return user_team;
    }

    public void setUser_team(String user_team) {
        this.user_team = user_team;
    }

    public Long getUser_money() {
        return user_money;
    }

    public void setUser_money(Long user_money) {
        this.user_money = user_money;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_team='" + user_team + '\'' +
                ", user_money=" + user_money +
                '}';
    }
}
