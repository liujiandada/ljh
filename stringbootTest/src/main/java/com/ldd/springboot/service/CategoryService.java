package com.ldd.springboot.service;

import com.ldd.springboot.entity.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> list(int start,int size ,String orderBy );

    public void save(Category category);

    public void delete(int id);

    public int update(Category category);

    public Category get(int id);
}