package com.ldd.springboot.filter;

import com.ldd.springboot.service.SysPermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;


public class URLPathMatchingFilter extends FormAuthenticationFilter {

    @Autowired
    SysPermissionService sysPermissionService ;

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {

        String requestURI = getPathWithinApplication(request);

		System.out.println("requestURI:" + requestURI);

        Subject subject = SecurityUtils.getSubject();

        // 如果没有登录，就跳转到登录页面
        if (!subject.isAuthenticated()) {
            return true;
        }

        // 看看这个路径权限里有没有维护，如果没有维护，一律放行
        boolean needInterceptor = sysPermissionService.needInterceptor(requestURI);
        if (!needInterceptor) {
            return true;
        } else {
            boolean hasPermission = false;
            String userName = subject.getPrincipal().toString();
            Set<String> permissionUrls = sysPermissionService.listPermissionURLByName(userName);
            for (String url : permissionUrls) {
                // 这就表示当前用户有这个权限
                if (url.equals(requestURI)) {
                    hasPermission = true;
                    break;
                }
            }
            if (hasPermission)
                return true;
            else {
                UnauthorizedException ex = new UnauthorizedException("当前用户没有访问路径 " + requestURI + " 的权限");
                subject.getSession().setAttribute("ex", ex);

                WebUtils.issueRedirect(request, response, "/unauthorized");
                return false;
            }

        }

    }
}
