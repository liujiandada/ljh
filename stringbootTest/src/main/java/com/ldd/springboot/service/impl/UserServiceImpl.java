package com.ldd.springboot.service.impl;

import com.ldd.springboot.entity.User;
import com.ldd.springboot.mapper.UserMapper;
import com.ldd.springboot.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujian
 * @since 2019-04-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;

   public User findByUsername(String username){
       return userMapper.findByname(username);
    }
}
