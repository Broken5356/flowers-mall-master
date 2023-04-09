package com.hide.backend.service.impl;

import com.hide.backend.dao.UserDao;
import com.hide.backend.entity.User;
import com.hide.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userdao;

    @Override
    public int add(User user) {
        try {
            return userdao.add(user);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int delete(int uid) {
        return userdao.delete(uid);
    }

    @Override
    public int update(User user) {
        return userdao.update(user);
    }

    @Override
    public List<User> find(String searchKey) {
        return userdao.find(searchKey);
    }

    @Override
    public User queryInfo(String account) {
        return userdao.queryInfo(account);
    }
}
