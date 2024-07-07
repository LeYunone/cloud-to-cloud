package com.leyunone.cloudcloud;

import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/4/5
 */
@SpringBootApplication
public class CloudToCloudApplication {

    public static void main(String[] args) {
        //FIXME 放开FastJSON针对安全符和安全类型的检查，后续仅设置局部
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        SpringApplication.run(CloudToCloudApplication.class, args);
    }

}
