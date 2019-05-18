package com.ldd.springboot.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import java.util.Map;
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
    SysRoleMapper sysRoleMapper;

    @Resource
    SysPermissionMapper sysPermissionMapper;


    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public User findByUserName(String username){
        User user = userMapper.findByUserName(username);
        return user;
    }


    /**
     * 根据UserId 查询用户详情
     * @param userId
     * @return
     */
    public User findByUserId(Long userId){
        User user = userMapper.selectById(userId);
        if(user != null){
            List<SysRole> roleList = sysRoleMapper.listSysRoleByRoleId(userId);
            if(roleList != null && roleList.size() > 0){
                user.setRoleList(roleList);
            }
            List<SysPermission> permissions = sysPermissionMapper.listPermissionByType(userId);
            for (SysPermission sysPermission: permissions) {
                Set set =  new HashSet();
                set.add(sysPermission.getPermission());
                user.setPermissions(set);
            }
            List<SysPermission> authMenu= recursion(userId,0l);

            if(authMenu != null && authMenu.size() > 0){
                user.setAuthMenu(authMenu);
            }
        }
        return user;
    }


    public IPage<User> selectALL(Page page, Map map){
        page.setRecords(userMapper.selectAll(page,map));
        return page;
    }


    private List<SysPermission> recursion( Long userId, Long parentId){

        List<SysPermission> sysPermissionList = sysPermissionMapper.listMenuByParentId(userId,parentId);
        if(sysPermissionList != null && sysPermissionList.size()>0){
            for (SysPermission sysPermission: sysPermissionList) {
                List<SysPermission> List = recursion(userId,sysPermission.getPermId());
                if(List != null && List.size()>0){
                    sysPermission.setChild(List);
                }
            }
        }
        return sysPermissionList;
    }

}
