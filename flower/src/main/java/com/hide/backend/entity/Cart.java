package com.hide.backend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 演员实体类
 **/
@Data
@Accessors(chain = true)
public class Cart {
    private int id;
    private int fid;
    private String flower;
    private int amount;
    private float price;
    // 用户 id
    private int uid;
    private String account;
}


