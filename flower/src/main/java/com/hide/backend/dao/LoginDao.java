package com.hide.backend.dao;

import com.hide.backend.entity.LoginForm;
import com.hide.backend.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDao {

    @Select("select * from users where account = #{account} and password = #{password} and role = #{role};")
    User login(LoginForm form);

}




