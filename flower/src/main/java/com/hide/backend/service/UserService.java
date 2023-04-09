package com.hide.backend.service;

import com.hide.backend.entity.User;

import java.util.List;


public interface UserService {

    int add(User user);
    int delete(int uid);
    int update(User user);
    List<User> find(String searchKey);
    User queryInfo(String account);
}
