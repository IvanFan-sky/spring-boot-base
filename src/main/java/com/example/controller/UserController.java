package com.example.controller;

import com.example.common.api.Result;
import com.example.dto.LoginDTO;
import com.example.entity.User;
import com.example.security.AuthService;
import com.example.security.UserPrincipal;
import com.example.service.UserService;
import com.example.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户控制器
 */
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags = "用户接口")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 操作结果
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<Void> register(@Validated @RequestBody User user) {
        userService.register(user);
        return Result.success();
    }

    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<LoginVO> login(@Validated @RequestBody LoginDTO loginDTO) {
        // 登录认证
        String token = authService.login(loginDTO.getUsername(), loginDTO.getPassword());

        // 获取用户信息
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // 构建返回对象
        LoginVO loginVO = new LoginVO()
                .setId(userPrincipal.getId())
                .setUsername(userPrincipal.getUsername())
                .setNickname(userPrincipal.getNickname())
                .setToken(token);

        return Result.success(loginVO);
    }

    /**
     * 用户登出
     * 实现步骤：
     * 1. 获取当前用户信息（用于日志记录）
     * 2. 清除认证上下文
     * 3. 返回成功响应
     *
     * @return 操作结果
     */
    @PostMapping("/logout")
    @ApiOperation("用户登出")
    public Result<Void> logout(HttpServletRequest request) {
        try {
            // 1. 获取当前用户信息（用于日志记录）
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                String username = authentication.getName();
                log.info("用户[{}]已成功登出系统", username);
            }

            // 2. 清除认证上下文
            authService.logout();

            return Result.success();
        } catch (Exception e) {
            log.error("用户登出失败", e);
            return Result.failed("登出失败：" + e.getMessage());
        }
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/info")
    @ApiOperation("获取当前用户信息")
    public Result<UserPrincipal> getCurrentUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return Result.success(userPrincipal);
    }

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    @ApiOperation("获取用户信息")
    public Result<User> getUser(@PathVariable Long id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    /**
     * 获取用户列表
     *
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param keyword 搜索关键词
     * @return 用户列表
     */
    @GetMapping("/list")
    @ApiOperation("获取用户列表")
    public Result<com.baomidou.mybatisplus.extension.plugins.pagination.Page<User>> list(
            @io.swagger.annotations.ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @io.swagger.annotations.ApiParam("每页数量") @RequestParam(defaultValue = "10") Integer pageSize,
            @io.swagger.annotations.ApiParam("搜索关键词") @RequestParam(required = false) String keyword
    ) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<User> page = userService.listUsers(pageNum, pageSize, keyword);
        return Result.success(page);
    }

    /**
     * 更新用户信息
     *
     * @param id 用户ID
     * @param user 用户信息
     * @return 操作结果
     */
    @PutMapping("/{id}")
    @ApiOperation("更新用户")
    public Result<Void> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.updateById(user);
        return Result.success();
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        return Result.success();
    }
}
