package com.ldd.springboot.service.impl;

import com.ldd.springboot.entity.SysPermission;
import com.ldd.springboot.entity.User;
import com.ldd.springboot.mapper.SysPermissionMapper;
import com.ldd.springboot.mapper.UserMapper;
import com.ldd.springboot.mapper.UserRoleMapper;
import com.ldd.springboot.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldd.springboot.service.UserRoleService;
import com.ldd.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Resource
    SysPermissionMapper sysPermissionMapper;


    /**
     * 判断地址是否在维护
     * @param requestURI
     * @return
     */
    @Override
    public boolean needInterceptor(String requestURI) {
        List<SysPermission> permissionList=sysPermissionMapper.selectByUrl(new String[]{requestURI});
        return permissionList == null || permissionList.size()<=0 ? true : false;
    }

}
