package com.ldd.springboot.dao;

import java.util.List;

import com.ldd.springboot.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    List<Category> findAll();

    public int save(Category category);

    public void delete(int id);

    public Category get(int id);

    public int update(Category category);

}