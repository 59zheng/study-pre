package org.example.sqlSession;


import org.example.executor.Executor;
import org.example.executor.SimpleExecutor;
import org.example.pojo.Configuration;


public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public SqlSession openSession() {

        // 1.创建执行器对象
        Executor simpleExecutor = new SimpleExecutor();

        // 2.生产sqlSession对象
        DefaultSqlSession defaultSqlSession = new DefaultSqlSession(configuration,simpleExecutor);

        return defaultSqlSession;
    }
}
