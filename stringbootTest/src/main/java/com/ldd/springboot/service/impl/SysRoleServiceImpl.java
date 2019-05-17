package com.ldd.springboot.service.impl;

import com.ldd.springboot.entity.SysRole;
import com.ldd.springboot.entity.User;
import com.ldd.springboot.mapper.SysPermissionMapper;
import com.ldd.springboot.mapper.SysRoleMapper;
import com.ldd.springboot.mapper.UserMapper;
import com.ldd.springboot.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Resource
    UserMapper userMapper;

    @Resource
    SysRoleMapper sysRoleMapper;

    /**
     * 根据用户名查询角色集合
     * @param userName
     * @return
     */
    @Override
    public List<SysRole> listRoleByUser(String userName) {
        User user=userMapper.findByUserName(userName);
        List<SysRole> sysRoleList=sysRoleMapper.listSysRoleByRoleId(user.getUserId());
        return sysRoleList;
    }


}
