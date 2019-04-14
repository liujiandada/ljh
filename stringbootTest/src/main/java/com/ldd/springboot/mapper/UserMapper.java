package com.ldd.springboot.mapper;

import com.ldd.springboot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2019-04-09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User findByname(String username);
}
