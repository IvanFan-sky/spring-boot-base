package com.example.security;

import com.example.entity.User;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定义用户详情服务
 * 实现Spring Security的UserDetailsService接口
 * 用于加载用户特定数据的核心接口
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    // 用户服务类
    private final UserService userService;

    /**
     * 根据用户名加载用户信息
     * 此方法会被Spring Security调用，用于加载认证用户的信息
     *
     * @param username 用户名
     * @return UserDetails Spring Security的用户详情对象
     * @throws UsernameNotFoundException 当用户不存在时抛出此异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中查询用户
        User user = userService.lambdaQuery()
                .eq(User::getUsername, username)
                .one();
                
        // 如果用户不存在，抛出异常
        if(user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        
        // 将我们的用户对象转换为Spring Security的UserDetails对象
        return UserPrincipal.create(user);
    }

    /**
     * 根据用户ID加载用户信息
     * 用于JWT token认证时，根据token中的用户ID获取用户信息
     *
     * @param id 用户ID
     * @return UserDetails Spring Security的用户详情对象
     * @throws UsernameNotFoundException 当用户不存在时抛出此异常
     */
    public UserDetails loadUserById(Long id) {
        // 从数据库中查询用户
        User user = userService.getById(id);
        
        // 如果用户不存在，抛出异常
        if(user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        
        // 将用户对象转换为UserDetails对象
        return UserPrincipal.create(user);
    }
}
