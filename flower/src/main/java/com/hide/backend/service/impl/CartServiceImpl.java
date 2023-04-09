package com.hide.backend.service.impl;

import com.hide.backend.dao.CartDao;
import com.hide.backend.dao.UserDao;
import com.hide.backend.entity.Cart;
import com.hide.backend.entity.User;
import com.hide.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartDao cartdao;
    @Autowired
    UserDao userDao;

    @Override
    public int add(Cart cart) {
        int uid = userDao.queryIdByAccount(cart.getAccount());
        cart.setUid(uid);
        Cart cart1 = cartdao.checkIsAdded(cart);  //检查是否已添加
        if (cart1==null){
            return cartdao.add(cart);
        }else {
            return cartdao.addAmount(cart);
        }
    }

    @Override
    public int delete(int uid) {
        return cartdao.delete(uid);
    }

    @Override
    public int update(Cart cart) {
        return cartdao.update(cart);
    }

    @Override
    public List<Cart> find(String searchKey,String account) {
        return cartdao.find(searchKey,account);
    }

    @Override
    public List<Cart> queryByAccount(String account) {
        Integer uid = userDao.queryIdByAccount(account);
        return cartdao.queryByUid(uid);
    }


}
