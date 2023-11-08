package org.example.util;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author yanzheng
 * @Date 2023/4/3 09:08
 */
public class NacosFileUtils {


    public static void main(String[] args) throws FileNotFoundException {

        InputStream permission = NacosFileUtils.class.getClassLoader().getResourceAsStream("导入规则.tsv");


        assert permission != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(permission));
       List<String> objects = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        reader.lines().forEach(line -> {
            String[] split = line.split("\t");
            split[1]=split[1].replace("\"","");

            map.put(split[0], Arrays.asList(split));
        });

        System.out.println(JSON.toJSONString(map));
        try {
            reader.close();
            permission.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
