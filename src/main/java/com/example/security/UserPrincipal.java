package com.example.security;

import com.example.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户认证信息主体类
 * 实现Spring Security的UserDetails接口
 * 用于封装用户的认证信息和权限信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    
    // 用户ID
    private Long id;
    
    // 用户名
    private String username;
    
    // 用户昵称
    private String nickname;
    
    // 密码（加@JsonIgnore注解防止序列化）
    @JsonIgnore
    private String password;
    
    // 用户状态（1：启用，0：禁用）
    private Integer status;
    
    // 用户权限集合
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 创建UserPrincipal实例
     * 将User实体类转换为UserPrincipal对象
     *
     * @param user 用户实体对象
     * @return UserPrincipal对象
     */
    public static UserPrincipal create(User user) {
        // 创建默认的用户权限（ROLE_USER）
        List<GrantedAuthority> authorities = Stream.of("ROLE_USER")
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 构建并返回UserPrincipal对象
        return UserPrincipal.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .status(user.getStatus())
                .authorities(authorities)
                .build();
    }

    /**
     * 获取用户权限集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * 获取用户密码
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * 获取用户名
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 判断账号是否未过期
     * 返回true表示未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 判断账号是否未锁定
     * 根据用户状态判断（status=1表示启用）
     */
    @Override
    public boolean isAccountNonLocked() {
        return Objects.equals(status, 1);
    }

    /**
     * 判断凭证是否未过期
     * 返回true表示未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 判断账号是否启用
     * 根据用户状态判断（status=1表示启用）
     */
    @Override
    public boolean isEnabled() {
        return Objects.equals(status, 1);
    }
}
