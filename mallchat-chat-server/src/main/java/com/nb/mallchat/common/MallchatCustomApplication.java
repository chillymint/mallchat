package com.nb.mallchat.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author
 * @date
 */
@SpringBootApplication(scanBasePackages = {"com.nb.mallchat"})
@MapperScan({"com.nb.mallchat.common.**.mapper"})
//@ServletComponentScan
public class MallchatCustomApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallchatCustomApplication.class,args);
    }

}