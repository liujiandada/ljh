package com.ldd.springboot.mapper;

import com.ldd.springboot.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * @param userId
     * @return
     */
    List<SysPermission> listPermissionByUserId(Long userId);

    /**
     * 根据userId  查询 buttom
     * @param userId
     * @return
     */
    List<SysPermission> listPermissionByType(Long userId);


    List<SysPermission> listMenuByParentId(@Param("userId")Long userId , @Param("parentId")Long parentId);

}
