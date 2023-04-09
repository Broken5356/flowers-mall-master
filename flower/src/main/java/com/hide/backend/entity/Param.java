package com.hide.backend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 查询参数
 **/
@Data
@Accessors(chain = true)
public class Param {
    private String searchKey;
    private int page;
}
