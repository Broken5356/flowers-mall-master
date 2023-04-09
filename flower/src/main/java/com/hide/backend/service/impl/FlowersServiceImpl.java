package com.hide.backend.service.impl;

import com.hide.backend.dao.FlowersDao;
import com.hide.backend.dao.SpeciesDao;
import com.hide.backend.entity.Flower;
import com.hide.backend.entity.Species;
import com.hide.backend.service.FlowersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FlowersServiceImpl implements FlowersService {

    @Autowired
    FlowersDao flowersDao;
    @Override
    public int add(Flower flower) {
        return flowersDao.add(flower);
    }

    @Override
    public int delete(int id) {
        return flowersDao.delete(id);
    }

    @Override
    public int update(Flower flower) {
        return flowersDao.update(flower);
    }

    @Override
    public List<Flower> find(String searchKey,String searchType) {
        return flowersDao.find(searchKey,searchType);
    }

    @Override
    public List<Flower> findAll(String searchKey) {
        return flowersDao.findAll(searchKey);
    }

    @Override
    public int updateImg(String img_guid,Integer id) {
        return flowersDao.updateImg(img_guid,id);
    }
}
