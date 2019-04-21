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
 * @since 2019-04-20
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<UserRole> findByUserId (Integer userId);

}
