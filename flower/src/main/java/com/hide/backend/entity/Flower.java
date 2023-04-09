package com.hide.backend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 演员实体类
 **/
@Data
@Accessors(chain = true)
public class Flower {
    private int id;
    private String name;
    // 分类名称
    private String species_name;
    private float price;
    // 详细介绍
    private String detail;
    // 图片
    private String img_guid;
    // 是否上架
    private int state;
}



