package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.exception.ApiException;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现类
 * 实现用户相关的业务逻辑
 *
 * @author example
 * @version 1.0.0
 * @since 2025-01-11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 密码加密器
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @throws ApiException 如果用户名已存在
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(User user) {
        log.info("开始注册用户: {}", user.getUsername());
        
        // 检查用户名是否已存在
        boolean exists = lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .exists();
        
        if (exists) {
            log.warn("用户名已存在: {}", user.getUsername());
            throw new ApiException("用户名已存在");
        }

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.debug("密码加密完成");
        
        // 设置用户状态为启用
        user.setStatus(1);
        
        // 保存用户信息
        save(user);
        log.info("用户注册成功: {}", user.getUsername());
    }

    /**
     * 分页查询用户列表
     *
     * @param pageNum   页码
     * @param pageSize  每页大小
     * @param keyword   关键字
     * @return 用户列表
     */
    @Override
    public Page<User> listUsers(Integer pageNum, Integer pageSize, String keyword) {
        log.info("查询用户列表: pageNum={}, pageSize={}, keyword={}", pageNum, pageSize, keyword);
        
        // 创建分页对象
        Page<User> page = new Page<>(pageNum, pageSize);
        
        // 创建查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .like(keyword != null, User::getUsername, keyword)
                .or()
                .like(keyword != null, User::getNickname, keyword)
                .orderByDesc(User::getCreateTime);
        
        // 执行分页查询
        Page<User> result = page(page, wrapper);
        log.info("查询到{}条用户记录", result.getTotal());
        return result;
    }
}
