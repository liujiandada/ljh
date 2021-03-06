package com.ldd.springboot.mapper;

import com.ldd.springboot.entity.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    List<RolePermission> findByRoleId(Long roleId);

}
