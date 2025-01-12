package com.example.security;

import com.example.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * 认证服务类
 * 处理用户认证相关的业务逻辑
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return JWT令牌
     */
    public String login(String username, String password) {
        // 创建认证token
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        // 认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 保存认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 生成JWT token
        return jwtUtil.generateToken(authentication);
    }

    /**
     * 登出处理
     * 清除认证信息
     */
    public void logout() {
        SecurityContextHolder.clearContext();
        log.debug("已清除认证上下文");
    }
}
