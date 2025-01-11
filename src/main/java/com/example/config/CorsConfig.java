package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 * 由于浏览器的同源策略限制，前后端分离开发时需要配置跨域支持
 */
@Configuration
public class CorsConfig {
    
    /**
     * 创建 CorsFilter Bean，用于处理跨域请求
     * @return CorsFilter 实例
     */
    @Bean
    public CorsFilter corsFilter() {
        // 创建 CorsConfiguration 对象，用于配置跨域相关参数
        CorsConfiguration config = new CorsConfiguration();
        
        // 允许来自前端开发服务器的跨域请求
        config.addAllowedOriginPattern("*");
        
        // 允许发送 Cookie
        config.setAllowCredentials(true);
        
        // 允许的请求方法
        config.addAllowedMethod("GET");     // 允许 GET 请求
        config.addAllowedMethod("POST");    // 允许 POST 请求
        config.addAllowedMethod("PUT");     // 允许 PUT 请求
        config.addAllowedMethod("DELETE");  // 允许 DELETE 请求
        config.addAllowedMethod("OPTIONS"); // 允许 OPTIONS 请求（预检请求）
        
        // 允许的请求头
        config.addAllowedHeader("*");
        
        // 设置跨域请求的有效期，单位为秒
        // 在有效期内，浏览器无需再次发送预检请求
        config.setMaxAge(3600L);
        
        // 创建 UrlBasedCorsConfigurationSource 对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
        // 对所有接口应用跨域配置
        source.registerCorsConfiguration("/**", config);
        
        // 返回 CorsFilter 实例
        return new CorsFilter(source);
    }
}
