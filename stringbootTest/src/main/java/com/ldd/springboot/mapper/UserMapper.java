package com.ldd.springboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.springboot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User findByUserName(String username);

    List<User> selectAll(Page page , Map map);
}
