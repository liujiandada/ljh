package com.ldd.springboot.mapper;

import com.ldd.springboot.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<SysPermission> selectByUrl(String [] urls);

    /**
     * 根据用户ID查询资源集合
     * @param userIds
     * @return
     */
    List<SysPermission> listPermissionByUserIds(String [] userIds);
}
