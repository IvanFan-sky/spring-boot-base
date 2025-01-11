package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 *
 * @author example
 * @version 1.0.0
 * @since 2025-01-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
