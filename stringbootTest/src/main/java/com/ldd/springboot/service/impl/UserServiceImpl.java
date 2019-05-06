package com.ldd.springboot.service.impl;

import com.ldd.springboot.entity.SysPermission;
import com.ldd.springboot.entity.SysRole;
import com.ldd.springboot.entity.User;
import com.ldd.springboot.entity.UserRole;
import com.ldd.springboot.mapper.*;
import com.ldd.springboot.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    UserRoleMapper userRoleMapper;

    @Resource
    SysRoleMapper sysRoleMapper;

    @Resource
    RolePermissionMapper rolePermissionMapper;

    @Resource
    SysPermissionMapper sysPermissionMapper;


    public User findByUserName(String username){
        return userMapper.findByUserName(username);
    }

    /**
     * 根据用户名查询角色集合
     * @param userName
     * @return
     */
    @Override
    public List<SysRole> listRoleByName(String userName) {
        Set<String> stringSet= new HashSet<>();
        User user=userMapper.findByUserName(userName);
        List<UserRole> userRoleList=userRoleMapper.findByUserId(user.getUserId());
        String [] strRoleIds = new String[userRoleList.size()];
        for (int i=0 ; i<userRoleList.size() ; i++) {
            strRoleIds[i]=String.valueOf(userRoleList.get(i).getRoleId());
        }
        List<SysRole> sysRoleList=sysRoleMapper.listSysRoleByRoleIds(strRoleIds);
        return sysRoleList;
    }

}
