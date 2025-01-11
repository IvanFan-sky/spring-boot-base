package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.api.Result;
import com.example.entity.User;
import com.example.dto.LoginDTO;
import com.example.service.UserService;
import com.example.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
        LoginVO loginVO = userService.login(loginDTO);
        return Result.success(loginVO);
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
    public Result<Page<User>> list(
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam("每页数量") @RequestParam(defaultValue = "10") Integer pageSize,
            @ApiParam("搜索关键词") @RequestParam(required = false) String keyword
    ) {
        Page<User> page = userService.listUsers(pageNum, pageSize, keyword);
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
