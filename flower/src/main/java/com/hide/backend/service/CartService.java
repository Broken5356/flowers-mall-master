package com.hide.backend.service;

import com.hide.backend.entity.Cart;

import java.util.List;


public interface CartService {

    int add(Cart cart);
    int delete(int uid);
    int update(Cart cart);
    List<Cart> find(String searchKey,String account);
    List<Cart> queryByAccount(String account);
}
