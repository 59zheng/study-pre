package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 映射配置类：存放mapper.xml解析内容
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MappedStatement {

    /**
    * 唯一标识: statementId (语句id):namespace.id (命名空间,对应不同的mapper)
    * */
    private String statementId;
    /**
    * 返回值的类型
    * */
    private String resultType;
    /**
    *参数的类型
    * */
    private String parameterType;

    /**
    * sql 语句
    * */
    private String sql;

    // sqlCommandType ：判断当前是什么操作的一个属性
    /**
    * sql Command type 操作属性 select update insert delete
    * */
    private String sqlCommandType;


}
