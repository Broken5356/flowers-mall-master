package com.hide.backend.controller;

import com.hide.backend.config.Constant;
import com.hide.backend.config.HttpMsg;
import com.hide.backend.dao.LoginDao;
import com.hide.backend.entity.LoginForm;
import com.hide.backend.entity.R;
import com.hide.backend.entity.User;
import com.hide.backend.util.VerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;


/**
 * 登录
 **/
@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    LoginDao loginDao;

    /**
     * 登录
     * @param form
     * @return
     */
    @RequestMapping("/doLogin")
    public R doLogin(@RequestBody LoginForm form) {
        R r = new R();
        if (!VerifyUtil.verifyLoginForm(form)) {
            return r.setCode(4000).setMsg(HttpMsg.ERROR_INPUT);
        }
        User loginUser = loginDao.login(form);
        if (loginUser != null) {
            return r.setCode(2000).setMsg("欢迎您：" + loginUser.getName()).setData(loginUser);
        }
        return r.setCode(4000).setMsg(HttpMsg.ERROR_VERIFY);
    }

    @RequestMapping("/test")
    public R test() {
        R r = new R();
        return r.setCode(4000).setMsg(Constant.IMG_PATH);
    }

}
