package com.zl.dao;

import com.zl.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户的持久层接口
 */
public interface IUserDao {

    /**
     * 查询所有用户
     * @return
     */
    List<User> findAll();

    /**
     * 查询用户是否登陆
     */
    User findUser(@Param("username")String username, @Param("password")String password);
}
