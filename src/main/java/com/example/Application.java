package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用程序入口类
 * 
 * @author example
 * @version 1.0.0
 * @since 2025-01-11
 */
@SpringBootApplication
@MapperScan("com.example.mapper") // 配置MyBatis的Mapper扫描路径
public class Application {
    /**
     * 应用程序主入口方法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
