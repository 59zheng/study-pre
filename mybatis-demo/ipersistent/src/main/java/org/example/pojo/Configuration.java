package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置类：存放核心配置文件解析出来的内容  sqlMapConfig
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Configuration {

    /**
    * 数据源对象
    * */
    private DataSource dataSource;

    /**
    * key: statementId:namespace.id  MappedStatement:封装好的MappedStatement对象
    * */
    private Map<String, MappedStatement> mappedStatementMap = new HashMap();


}
