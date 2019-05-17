package com.ldd.springboot.service;

import com.ldd.springboot.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> listRoleByUser(String userName);

}
