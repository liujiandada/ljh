package com.ldd.springboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.springboot.entity.SysRole;
import com.ldd.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
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

    User findByUserId(Long userId);

    IPage<User> selectALL(Page page, Map map);
}
