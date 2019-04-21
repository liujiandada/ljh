package com.ldd.springboot.service.impl;

import com.ldd.springboot.entity.UserRole;
import com.ldd.springboot.mapper.UserRoleMapper;
import com.ldd.springboot.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujian
 * @since 2019-04-20
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
