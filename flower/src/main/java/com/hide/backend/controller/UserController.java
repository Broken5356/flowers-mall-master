package com.hide.backend.controller;

import com.hide.backend.config.Constant;
import com.hide.backend.config.HttpMsg;
import com.hide.backend.dao.LoginDao;
import com.hide.backend.dao.UserDao;
import com.hide.backend.entity.LoginForm;
import com.hide.backend.entity.Param;
import com.hide.backend.entity.R;
import com.hide.backend.entity.User;
import com.hide.backend.service.UserService;
import com.hide.backend.util.VerifyUtil;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户
 **/
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserDao dao;

    @RequestMapping("/test")
    public R test() {
        R r = new R();
        return r.setCode(4000).setMsg(Constant.IMG_PATH).setData(dao.findAll());
    }

    @RequestMapping("/queryInfoByAccount")
    public R queryInfoByAccount(@RequestParam("account") String account) {
        R r = new R();
        if (StringUtil.isEmpty(account)){
            return r.setCode(4000).setMsg(HttpMsg.INVALID_PARAM);
        }
        User loginUser = userService.queryInfo(account);
        if (loginUser == null){
            return r.setCode(4000).setMsg(HttpMsg.INVALID_USER);
        }
        return r.setCode(2000).setData(loginUser);
    }


    @RequestMapping("/find")
    public R find(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey) {
        R r = new R();
        Map<String, Object> map = new HashMap<>();
        List<User> users = userService.find(searchKey);
        if (users == null) {
            return r.setCode(2000);
        }
        List<User> items = users.size() >= page * Constant.PAGE_SIZE ?
                users.subList((page - 1) * Constant.PAGE_SIZE, page * Constant.PAGE_SIZE)
                : users.subList((page - 1) * Constant.PAGE_SIZE, users.size());
        int len = users.size() % Constant.PAGE_SIZE == 0 ? users.size() / Constant.PAGE_SIZE
                : (users.size() / Constant.PAGE_SIZE + 1);
        map.put("items", items);
        map.put("len", len);
        return r.setCode(2000).setData(map);
    }


    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping("/create")
    public R create(@RequestBody User user) {
        R r = new R();
        int ans = userService.add(user);
        if (ans == 1) {
            return r.setCode(2000).setMsg(HttpMsg.ADD_USER_OK);
        }
        return r.setCode(4000).setMsg(HttpMsg.ADD_USER_FAILED);
    }

    @RequestMapping("/update")
    public R update(@RequestBody User user) {
        R r = new R();
        int ans = userService.update(user);
        if (ans >= 0) {
            return r.setCode(2000).setMsg(HttpMsg.UPDATE_USER_OK);
        }
        return r.setCode(4000).setMsg(HttpMsg.UPDATE_USER_FAILED);
    }


    @DeleteMapping("/delete")
    public R delete(@RequestParam("id") int id) {
        R r = new R();
        int ans = userService.delete(id);
        if (ans == 1) {
            return r.setCode(2000).setMsg(HttpMsg.DELETE_USER_OK);
        }
        return r.setCode(4000).setMsg(HttpMsg.DELETE_USER_FAILED);
    }
}

