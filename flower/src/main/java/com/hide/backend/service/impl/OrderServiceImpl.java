package com.hide.backend.service.impl;

import com.hide.backend.dao.OrderDao;
import com.hide.backend.dao.UserDao;
import com.hide.backend.entity.Cart;
import com.hide.backend.entity.Order;
import com.hide.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderdao;
    @Autowired
    UserDao userDao;

    @Override
    public int add(Cart cart) {
        return orderdao.add(cart);
    }

    @Override
    public int delete(int uid) {
        return orderdao.delete(uid);
    }

    @Override
    public int update(Order order) {
        return orderdao.update(order);
    }

    @Override
    public List<Order> find(String searchKey,String account) {
        Integer uid = userDao.queryIdByAccount(account);
        return orderdao.find(searchKey,uid);
    }

    @Override
    public List<Order> findAll(String searchKey) {
        return orderdao.findAll(searchKey);
    }

    @Override
    public List<Order> queryByAccount(String account) {
        Integer uid = userDao.queryIdByAccount(account);
        return orderdao.queryByUid(uid);
    }


}
