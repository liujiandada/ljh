package com.ldd.springboot.service;

import com.ldd.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
public interface UserService extends IService<User> {

    User findByUserName(String username);

    Set<String> listPermissionURLByName(String userName);

}
