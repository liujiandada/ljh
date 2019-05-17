package com.ldd.springboot.mapper;

import com.ldd.springboot.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据ID查询角色
     * @param userId
     * @return
     */
    List<SysRole> listSysRoleByRoleId(Long userId);

}
