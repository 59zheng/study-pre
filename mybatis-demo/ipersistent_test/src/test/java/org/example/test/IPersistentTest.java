package org.example.test;

import org.example.io.Resources;
import org.example.pojo.User;
import org.example.sqlSession.SqlSession;
import org.example.sqlSession.SqlSessionFactory;
import org.example.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @Author yanzheng
 * @Date 2023/2/7 16:05
 */
public class IPersistentTest {



    /**
     * 传统方式（不使用mapper代理）测试
     */
    @Test
    public void test1() throws Exception {

        // 1.根据配置文件的路径，加载成字节输入流，存到内存中 注意：配置文件还未解析
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");

        // 2.解析了配置文件，封装了Configuration对象  2.创建sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);

        // 3.生产sqlSession 创建了执行器对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4.调用sqlSession方法
        User user = new User();
        user.setId(1L);
        user.setUsername("tom");
    /*    User user2 = sqlSession.selectOne("user.selectOne", user);

        System.out.println(user2);*/
        List<User> list = sqlSession.selectList("user.findAll");
        for (User user1 : list) {
            System.out.println(user1);
        }

        // 5.释放资源
        sqlSession.close();


    }

}
