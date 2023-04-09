package com.hide.backend.service;

import com.hide.backend.entity.Flower;

import java.util.List;


public interface FlowersService {

    int add(Flower flower);
    int delete(int id);
    int update(Flower flower);
    List<Flower> find(String searchKey,String searchType);
    List<Flower> findAll(String searchKey);
    int updateImg(String img_guid,Integer id);
}
