package com.ldd.springboot.mapper;

import com.ldd.springboot.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<UserRole> findByUserId (Long userId);

}
