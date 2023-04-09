package com.hide.backend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 统一返回对象
 **/
@Data
@Accessors(chain = true)
public class R {
    private int code;
    private String msg;
    private Object data;
}



