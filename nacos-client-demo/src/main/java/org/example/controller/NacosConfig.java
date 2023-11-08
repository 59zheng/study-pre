package org.example.controller;

import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @Author yanzheng
 * @Date 2023/3/29 16:04
 */
@Controller
@RequestMapping("/afs/api/config")

public class NacosConfig {

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;

    @NacosConfigListener(dataId = "afs-test")
    public void onMessage(String msg){
        System.out.println(msg);
    }

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public boolean get() {
        System.out.println(useLocalCache);
        return useLocalCache;
    }


}
