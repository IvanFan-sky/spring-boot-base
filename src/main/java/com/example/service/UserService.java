package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.LoginDTO;
import com.example.entity.User;
import com.example.vo.LoginVO;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {
    
    /**
     * 用户注册
     *
     * @param user 用户信息
     */
    void register(User user);

    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 分页查询用户列表
     *
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param keyword 搜索关键词
     * @return 分页结果
     */
    Page<User> listUsers(Integer pageNum, Integer pageSize, String keyword);
}
