package com.ldd.springboot.mapper;

import com.ldd.springboot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
public interface UserMapper extends BaseMapper<User> {

    User findByname(String username);

}
