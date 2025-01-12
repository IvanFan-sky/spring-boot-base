package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.User;

/**
 * 用户服务接口
 *
 * @author example
 * @version 1.0.0
 * @since 2025-01-11
 */
public interface UserService extends IService<User> {
    
    /**
     * 用户注册
     *
     * @param user 用户信息
     */
    void register(User user);

    /**
     * 分页查询用户列表
     *
     * @param pageNum   页码
     * @param pageSize  每页大小
     * @param keyword   关键字
     * @return 用户列表
     */
    Page<User> listUsers(Integer pageNum, Integer pageSize, String keyword);
}
