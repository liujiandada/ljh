package com.ldd.springboot.filter;

import com.ldd.springboot.entity.User;
import com.ldd.springboot.service.SysPermissionService;
import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;
@Data
@Component
@ConfigurationProperties(prefix = "shiro", ignoreUnknownFields = false)
@PropertySource("classpath:shiro.properties")
public class URLPathMatchingFilter extends  PathMatchingFilter {

    @Value("${shiro.anon}")
    private String anon;

    @Autowired
    SysPermissionService sysPermissionService ;

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {

        String requestURI = getPathWithinApplication(request);

		System.out.println("requestURI:" + requestURI);
        Subject subject = SecurityUtils.getSubject();
        String [] stranon=anon.split(",");
        for(String str:stranon){
            if(str.equals(requestURI)){
                return true;
            }
        }
        // 如果没有登录，就跳转到登录页面
        if (!subject.isAuthenticated()) {
            request.setAttribute("code","001");
            request.getRequestDispatcher("/unauth").forward(request,response);
            return false;
        }

        // 看看这个路径权限里有没有维护，如果没有维护，一律放行
        boolean needInterceptor = sysPermissionService.needInterceptor(requestURI);
        if (!needInterceptor) {
            return true;
        } else {
            boolean hasPermission = false;
            User user = (User)subject.getPrincipal();
            Set<String> permissionUrls = sysPermissionService.listPermissionURLByName(user.getUserName());
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
                request.setAttribute("code","002");
                request.getRequestDispatcher("/unauth").forward(request,response);
                return false;
            }

        }

    }
}
