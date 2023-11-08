package org.example.dao;

import org.example.pojo.User;

import java.util.List;

/**
 * @Author yanzheng
 * @Date 2023/2/7 14:01
 */
public interface IUserDao {


    /**
     * 查询所有
     */
    List<User> findAll() throws Exception;

    /**
     * 根据多条件查询
     */
    User findByCondition(User user) throws Exception;


}
