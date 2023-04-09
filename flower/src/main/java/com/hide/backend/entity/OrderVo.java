package com.hide.backend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 演员实体类
 **/
@Data
@Accessors(chain = true)
public class OrderVo extends Order{
    private String username;
    private String phone;
    private String address;
}



