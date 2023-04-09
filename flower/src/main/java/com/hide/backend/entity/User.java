package com.hide.backend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 会员实体类
 **/
@Data
@Accessors(chain = true)
public class User {
    private int id;
    private String account;
    private String name;
    private String password;
    private String phone;
    private String address;
    private String role;
}



