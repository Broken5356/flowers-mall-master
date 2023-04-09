package com.hide.backend.controller;

import com.hide.backend.config.Constant;
import com.hide.backend.config.HttpMsg;
import com.hide.backend.dao.CartDao;
import com.hide.backend.dao.FlowersDao;
import com.hide.backend.entity.R;
import com.hide.backend.entity.Cart;
import com.hide.backend.service.CartService;
import com.hide.backend.service.OrderService;
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
@RequestMapping("cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;
    @Autowired
    CartDao dao;
    @Autowired
    FlowersDao flowersDao;

    @RequestMapping("/test")
    R test() {
        R r = new R();
        return r.setCode(4000).setMsg(Constant.IMG_PATH).setData(dao.findAll());
    }

    @RequestMapping("/queryByAccount")
    public R queryByAccount(@RequestParam("account") String account) {
        R r = new R();
        if (StringUtil.isEmpty(account)) {
            return r.setCode(4000).setMsg(HttpMsg.INVALID_PARAM);
        }
        List<Cart> carts = cartService.queryByAccount(account);
        for (Cart cart : carts) {
            float price = flowersDao.queryPrice(cart.getFid());
            cart.setPrice(cart.getAmount() * price);
        }
        return r.setCode(2000).setData(carts);
    }


    @RequestMapping("/find")
    public R find(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey, @RequestParam("account") String account) {
        R r = new R();
        Map<String, Object> map = new HashMap<>();
        List<Cart> carts = cartService.find(searchKey, account);
        if (carts == null) {
            return r.setCode(2000);
        }
        List<Cart> items = carts.size() >= page * Constant.PAGE_SIZE ?
                carts.subList((page - 1) * Constant.PAGE_SIZE, page * Constant.PAGE_SIZE)
                : carts.subList((page - 1) * Constant.PAGE_SIZE, carts.size());
        int len = carts.size() % Constant.PAGE_SIZE == 0 ? carts.size() / Constant.PAGE_SIZE
                : (carts.size() / Constant.PAGE_SIZE + 1);
        map.put("items", items);
        map.put("len", len);
        return r.setCode(2000).setData(map);
    }

    @RequestMapping("/buy")
    public R buy(@RequestParam("account") String account) {
        R r = new R();
        // 查该用户的购物车
        List<Cart> carts = (List<Cart>) queryByAccount(account).getData();
        for (Cart cart : carts) {
            // 增加订单数据
            orderService.add(cart);
            // 删除购物车数据
            cartService.delete(cart.getId());
        }
        return r.setCode(2000).setMsg(HttpMsg.BUY_OK);
    }


    @RequestMapping("/create")
    public R create(@RequestBody Cart cart) {
        R r = new R();
        int ans = cartService.add(cart);
        if (ans == 1) {
            return r.setCode(2000).setMsg(HttpMsg.ADD_CART_OK);
        }
        return r.setCode(4000).setMsg(HttpMsg.ADD_CART_FAILED);
    }

    @RequestMapping("/update")
    public R update(@RequestBody Cart cart) {
        R r = new R();
        int ans = cartService.update(cart);
        if (ans >= 0) {
            return r.setCode(2000).setMsg(HttpMsg.UPDATE_USER_OK);
        }
        return r.setCode(4000).setMsg(HttpMsg.UPDATE_USER_FAILED);
    }


    @DeleteMapping("/delete")
    public R delete(@RequestParam("id") int id) {
        R r = new R();
        int ans = cartService.delete(id);
        if (ans == 1) {
            return r.setCode(2000).setMsg(HttpMsg.DELETE_USER_OK);
        }
        return r.setCode(4000).setMsg(HttpMsg.DELETE_USER_FAILED);
    }
}

