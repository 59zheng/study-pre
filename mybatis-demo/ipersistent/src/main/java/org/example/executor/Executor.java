package org.example.executor;


import org.example.pojo.Configuration;
import org.example.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

public interface Executor { 

    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception;

    void close();
}
