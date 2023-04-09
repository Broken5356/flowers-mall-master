package com.hide.backend.dao;

import com.hide.backend.entity.LoginForm;
import com.hide.backend.entity.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    @Select("select * from users where (account like concat('%',#{searchKey},'%')  or name like concat('%',#{searchKey},'%')) and role = 'user';")
    List<User> find(String searchKey);

    @Select("select * from users where id = #{id};")
    User queryById(Integer id);

    @Select("select * from users;")
    List<User> findAll();

    @Select("select * from users where account = #{account} and role = 'user';")
    User queryInfo(String account);

    @Select("select id from users where account = #{account} and role = 'user';")
    Integer queryIdByAccount(String account);

    @Update("update users set name = #{name},password = #{password},phone = #{phone},address = #{address} where id = #{id};")
    int update(User user);

    @Delete("delete from users where id = #{id};")
    int delete(int id);

    @Insert("insert into users(account,name,password,phone,address,role) " +
            "values(#{account},#{name},#{password},#{phone},#{address},'user');")
    int add(User user);

}




