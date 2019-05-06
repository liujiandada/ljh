package com.ldd.springboot.service;

import com.ldd.springboot.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
public interface SysPermissionService extends IService<SysPermission> {

   boolean needInterceptor (String requestURI);

   Set<String> listPermissionURLByName(String userName);
}
