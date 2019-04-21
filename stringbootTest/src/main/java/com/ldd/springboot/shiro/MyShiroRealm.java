package com.ldd.springboot.shiro;

import com.ldd.springboot.entity.*;
import com.ldd.springboot.mapper.RolePermissionMapper;
import com.ldd.springboot.mapper.SysPermissionMapper;
import com.ldd.springboot.mapper.SysRoleMapper;
import com.ldd.springboot.mapper.UserRoleMapper;
import com.ldd.springboot.service.impl.UserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 *
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserServiceImpl userService;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysPermissionMapper sysPermissionMapper;


    @Override
    public String getName() {
        return "MyShiroRealm";
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        List<UserRole> userRoleList= userRoleMapper.findByUserId(user.getUserId());
        for (UserRole userRole : userRoleList){
            int roleId=userRole.getRoleId();
            SysRole sysRole=sysRoleMapper.selectById(roleId);
            authorizationInfo.addRole(sysRole.getRoleName());
            List<RolePermission> rolePermissionList = rolePermissionMapper.findByRoleId(userRole.getRoleId());
            for (RolePermission rolePermission : rolePermissionList){
                int permId = rolePermission.getPermId();
                SysPermission sysPermission=sysPermissionMapper.selectById(permId);
                authorizationInfo.addStringPermission(sysPermission.getPermission());
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        String username = (String)token.getPrincipal();

        User user = userService.findByUsername(username);

        if (user == null) { //用户不存在
            throw new AuthenticationException();
        }
        if ("1".equals(user.getLogPower().trim())) { //禁止登录网页端
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );
        return authenticationInfo;
    }

    /**
     * 重置用户权限信息
     * @param username
     */
    public void removeUserAuthorizationInfoCache(String username) {
        SimplePrincipalCollection pc = new SimplePrincipalCollection();
        pc.add(username, super.getName());
        super.clearCachedAuthorizationInfo(pc);
    }


}