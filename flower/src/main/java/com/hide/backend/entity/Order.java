package com.hide.backend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 演员实体类
 **/
@Data
@Accessors(chain = true)
public class Order {
    private int id;
    private String order_guid;
    private String flower;
    private int amount;
    private float price;
    private float state;
    // 用户 id
    private int uid;
}



