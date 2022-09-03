package com.linxz.doexcel.entity;

import com.alibaba.excel.annotation.ExcelProperty;

public class UserMoney {
    @ExcelProperty(value = "业务员", index = 3)
    private String user_name;

    @ExcelProperty(value = "预收款", index = 4)
    private Long user_money;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Long getUser_money() {
        return user_money;
    }

    public void setUser_money(Long user_money) {
        this.user_money = user_money;
    }

    @Override
    public String toString() {
        return "UserMoney{" +
                "user_name='" + user_name + '\'' +
                ", user_money='" + user_money + '\'' +
                '}';
    }
}
