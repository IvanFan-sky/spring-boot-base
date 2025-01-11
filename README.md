# Spring Boot Base Project

这是一个基于Spring Boot的基础项目，集成了常用的功能和最佳实践。

## 技术栈

- Spring Boot 2.7.5
- MyBatis-Plus 3.5.2
- MySQL 8.0
- Knife4j 3.0.3
- Hutool 5.8.10
- JWT 0.9.1
- Lombok

## 功能特性

- 统一响应处理
- 全局异常处理
- 接口文档（Knife4j）
- 用户管理（CRUD）
- 日志记录
- 参数校验
- 分页查询

## 快速开始

1. 克隆项目
2. 创建MySQL数据库，执行`src/main/resources/db/schema.sql`
3. 修改`application.yml`中的数据库配置
4. 运行项目
5. 访问接口文档：http://localhost:8080/api/doc.html

## 项目结构

```
spring-boot-base
├── src/main/java
│   └── com.example
│       ├── common          // 公共模块
│       │   ├── api        // 通用API相关类
│       │   └── exception  // 异常处理
│       ├── config         // 配置类
│       ├── controller     // 控制器
│       ├── entity         // 实体类
│       ├── mapper         // MyBatis接口
│       └── service        // 服务层
├── src/main/resources
│   ├── application.yml    // 应用配置
│   └── db                // 数据库脚本
└── pom.xml
```

## API文档

启动项目后，访问：http://localhost:8080/api/doc.html

## 开发规范

1. 统一返回格式
2. 统一异常处理
3. 规范的命名约定
4. 完善的注释和文档

## 注意事项

1. 生产环境部署前请修改配置
2. 注意保护敏感信息
3. 建议使用HTTPS
4. 定期备份数据库
