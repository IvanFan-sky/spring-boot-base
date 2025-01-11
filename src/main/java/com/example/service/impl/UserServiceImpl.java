package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.exception.ApiException;
import com.example.dto.LoginDTO;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.example.util.JwtUtil;
import com.example.util.MD5Util;
import com.example.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Service  // 标记这是一个服务类，会被Spring自动扫描并注册为Bean
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * JWT工具类，用于生成和验证token
     */
    private final JwtUtil jwtUtil;

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

        // 对密码进行MD5加密
        user.setPassword(MD5Util.encode(user.getPassword()));
        log.debug("密码加密完成");
        
        // 设置用户状态为启用
        user.setStatus(1);
        
        // 保存用户信息
        save(user);  // 这个save方法继承自ServiceImpl
        log.info("用户注册成功: {}", user.getUsername());
    }

    /**
     * 登录
     *
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        log.info("用户登录: {}", loginDTO.getUsername());
        
        // 根据用户名查询用户
        User user = lambdaQuery()
                .eq(User::getUsername, loginDTO.getUsername())
                .one();

        // 验证用户是否存在
        if (user == null) {
            log.warn("用户名不存在: {}", loginDTO.getUsername());
            throw new ApiException("用户名或密码错误");
        }

        // 验证密码是否正确
        if (!MD5Util.matches(loginDTO.getPassword(), user.getPassword())) {
            log.warn("密码错误, 用户名: {}", loginDTO.getUsername());
            throw new ApiException("用户名或密码错误");
        }

        // 验证用户状态
        if (user.getStatus() != 1) {
            log.warn("账号已被禁用, 用户名: {}", loginDTO.getUsername());
            throw new ApiException("账号已被禁用");
        }

        // 生成JWT token
        String token = jwtUtil.generateToken(user.getId());
        log.debug("生成token成功");

        // 返回登录结果
        LoginVO loginVO = new LoginVO()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setNickname(user.getNickname())
                .setToken(token);
                
        log.info("用户登录成功: {}", loginDTO.getUsername());
        return loginVO;
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
        
        // 执行分页查询并返回结果
        Page<User> result = page(page, wrapper);  // 这个page方法继承自ServiceImpl
        log.info("查询到{}条用户记录", result.getTotal());
        return result;
    }
}
