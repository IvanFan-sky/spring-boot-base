package com.example.util;

import com.example.security.UserPrincipal;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT工具类
 * 用于生成和解析JWT token
 */
@Slf4j
@Component
public class JwtUtil {

    /**
     * JWT密钥
     * 从配置文件中读取，用于token的签名
     */
    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * token有效期（毫秒）
     * 默认24小时
     */
    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    /**
     * 生成JWT token
     * 生成过程：
     * 1. 设置token的主题（用户ID）
     * 2. 设置token的签发时间
     * 3. 设置token的过期时间
     * 4. 使用密钥对token进行签名
     *
     * @param authentication 用户认证信息
     * @return JWT token
     */
    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // 获取当前时间
        Date now = new Date();
        // 计算过期时间
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        // 构建并返回token
        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))  // 设置主题（用户ID）
                .setIssuedAt(new Date())                    // 设置签发时间
                .setExpiration(expiryDate)           // 设置过期时间
                .signWith(SignatureAlgorithm.HS512, jwtSecret)  // 设置签名
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
                .setSigningKey(jwtSecret)
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
     * @param authToken JWT token
     * @return token是否有效
     */
    public boolean validateToken(String authToken) {
        try {
            // 解析token（如果token无效会抛出异常）
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        }
        return false;
    }
}
