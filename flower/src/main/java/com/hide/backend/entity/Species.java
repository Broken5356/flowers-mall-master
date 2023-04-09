package com.hide.backend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 花朵种类实体类
 **/
@Data
@Accessors(chain = true)
public class Species {
    private int id;
    private String species_name;

}



