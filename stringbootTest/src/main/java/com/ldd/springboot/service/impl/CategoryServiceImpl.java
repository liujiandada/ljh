package com.ldd.springboot.service.impl;

import com.ldd.springboot.entity.Category;
import com.ldd.springboot.mapper.CategoryMapper;
import com.ldd.springboot.service.CategoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@CacheConfig(cacheNames="category")
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;

    @Override
   @Cacheable(key="'category -'+ #p0 +'-'+#p1+'-'+#p2")
    public List<Category>list(int start, int size , String orderBy ) {
        List<Category> list=categoryMapper.findAll();
        return list;
    }

    @Override
    @Cacheable(key="'category '+ #p0")
    public Category get(int id) {
        Category c =categoryMapper.get(id);
        return c;
    }


    @Override
    @CacheEvict(allEntries=true)
//  @CachePut(key="'category '+ #p0")
    public void save(Category category) {
        categoryMapper.save(category);
    }

    @Override
    @CacheEvict(allEntries=true)
    public int update(Category category) {
        return categoryMapper.update(category);
    }

    @Override
    @CacheEvict(allEntries=true)
//    @CacheEvict(key="'category '+ #p0")
    public void delete(int id) {
        categoryMapper.delete(id);
    }

}