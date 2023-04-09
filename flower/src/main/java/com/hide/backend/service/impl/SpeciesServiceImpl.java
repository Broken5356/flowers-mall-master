package com.hide.backend.service.impl;

import com.hide.backend.dao.SpeciesDao;
import com.hide.backend.entity.Species;
import com.hide.backend.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SpeciesServiceImpl implements SpeciesService {

    @Autowired
    SpeciesDao speciesDao;

    @Override
    public int add(Species species) {
        return speciesDao.add(species);
    }

    @Override
    public int delete(int uid) {
        return speciesDao.delete(uid);
    }

    @Override
    public int update(Species species) {
        return speciesDao.update(species);
    }

    @Override
    public List<Species> find(String searchKey) {
        return speciesDao.find(searchKey);
    }

    @Override
    public List<Species> findAll() {
        return speciesDao.findAll();
    }
}
