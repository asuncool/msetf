package com.example.etf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Morningstar ETF数据解析系统
 * Spring Boot启动类
 */
@SpringBootApplication
@MapperScan("com.example.etf.mapper")
public class MsetfApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsetfApplication.class, args);
        System.out.println("========================================");
        System.out.println("Morningstar ETF Data Parser 启动成功!");
        System.out.println("API接口: http://localhost:8080/api/etf/parse");
        System.out.println("健康检查: http://localhost:8080/api/etf/health");
        System.out.println("========================================");
    }
}
