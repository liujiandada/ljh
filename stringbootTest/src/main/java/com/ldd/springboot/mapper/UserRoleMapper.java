package com.ldd.springboot.mapper;

import com.ldd.springboot.entity.UserRole;
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
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据userId查询用户角色关联
     * @param userId
     * @return
     */
    List<UserRole> findByUserId (Long userId);

}
