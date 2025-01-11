package com.example.util;

import org.springframework.util.DigestUtils;

/**
 * MD5工具类
 * 用于密码加密和验证
 */
public class MD5Util {
    
    /**
     * 密码加密的盐值前缀
     * 用于增加密码的复杂度，防止彩虹表攻击
     */
    private static final String SALT_PREFIX = "SPRING_BOOT_BASE";
    
    /**
     * 密码加密的盐值后缀
     */
    private static final String SALT_SUFFIX = "!@#$%^&*";
    
    /**
     * 对密码进行MD5加密
     * 加密过程：
     * 1. 将原始密码和盐值拼接
     * 2. 使用Spring的DigestUtils进行MD5加密
     * 3. 将结果转换为32位小写字符串
     *
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String encode(String password) {
        // 拼接盐值
        String saltPassword = SALT_PREFIX + password + SALT_SUFFIX;
        // 进行MD5加密并返回
        return DigestUtils.md5DigestAsHex(saltPassword.getBytes());
    }
    
    /**
     * 验证密码是否正确
     * 验证过程：
     * 1. 对输入的密码进行MD5加密
     * 2. 将加密结果与数据库中存储的密码进行比较
     *
     * @param password 输入的原始密码
     * @param encodedPassword 数据库中存储的加密密码
     * @return 密码是否匹配
     */
    public static boolean matches(String password, String encodedPassword) {
        // 对输入的密码进行加密
        String newPassword = encode(password);
        // 比较两个加密后的密码是否相同
        return newPassword.equals(encodedPassword);
    }
}
