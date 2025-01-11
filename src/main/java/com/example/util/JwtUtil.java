package com.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT工具类
 * 用于生成和解析JWT token
 */
@Component
public class JwtUtil {
    
    /**
     * JWT密钥
     * 从配置文件中读取，用于token的签名
     */
    @Value("${jwt.secret:your-secret-key}")
    private String secret;
    
    /**
     * token有效期（毫秒）
     * 默认24小时
     */
    @Value("${jwt.expiration:86400000}")
    private long expiration;
    
    /**
     * 生成JWT token
     * 生成过程：
     * 1. 设置token的主题（用户ID）
     * 2. 设置token的签发时间
     * 3. 设置token的过期时间
     * 4. 使用密钥对token进行签名
     *
     * @param userId 用户ID
     * @return JWT token
     */
    public String generateToken(Long userId) {
        // 获取当前时间
        Date now = new Date();
        // 计算过期时间
        Date expiryDate = new Date(now.getTime() + expiration);
        
        // 构建并返回token
        return Jwts.builder()
                .setSubject(String.valueOf(userId))  // 设置主题（用户ID）
                .setIssuedAt(now)                    // 设置签发时间
                .setExpiration(expiryDate)           // 设置过期时间
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())  // 设置签名
                .compact();
    }
    
    /**
     * 从token中获取用户ID
     * 解析过程：
     * 1. 验证token的签名
     * 2. 检查token是否过期
     * 3. 从token中提取用户ID
     *
     * @param token JWT token
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        // 解析token
        Claims claims = Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
        
        // 返回用户ID
        return Long.parseLong(claims.getSubject());
    }
    
    /**
     * 验证token是否有效
     * 验证过程：
     * 1. 验证token的签名
     * 2. 检查token是否过期
     *
     * @param token JWT token
     * @return token是否有效
     */
    public boolean validateToken(String token) {
        try {
            // 解析token（如果token无效会抛出异常）
            Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token);
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
