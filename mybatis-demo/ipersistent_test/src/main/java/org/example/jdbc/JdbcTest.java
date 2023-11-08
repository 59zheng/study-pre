package org.example.jdbc;

import lombok.Data;

import java.sql.*;

/**
 * @Author yanzheng
 * @Date 2023/2/7 10:19
 */
public class JdbcTest {


    /**
     * jdbc流程代码
     * 问题:
     *  1. 硬编码: 数据库驱动,数据库连接地址都是写死的
     *  2. 频繁创建销毁连接的问题,使用数据库连接池来进行连接的管理,维护,调度
     *  3. 参数,sql 也都是硬编码,需要进行配置文件映射
     *  4. 手动封装结果集 比较费劲
     *
     */
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //记载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            //通过驱动类,获取数据库链接
            connection = DriverManager.getConnection("jdbc:mysql://43.138.86.137:3306/study?characterEncoding=utf-8", "root", "123456");
            // 定义sql语句?表示占位符
            String sql = "select * from user where user_name = ?";
            // 获取预处理statement
            preparedStatement = connection.prepareStatement(sql);
            // 设置参数 替换?
            preparedStatement.setString(1, "tom");
            //向数据库发送请求,获取结果
            resultSet = preparedStatement.executeQuery();
            // 封装出来返回集
            // 遍历查询结果集
            User user = new User();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("user_name"); // 封装User
                user.setId(id);
                user.setUserName(username);
            }
            System.out.println(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    @Data
    public static class User {
        String userName;
        int id;
    }


}
