package com.ldd.springboot.mapper;

import com.ldd.springboot.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> findAll();

    public int save(Category category);

    public void delete(int id);

    public Category get(int id);

    public int update(Category category);

}