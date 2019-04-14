package com.ldd.springboot.generate;

import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;

import java.util.Map;

/**
 * 可编辑自定义模板
 */
public class Template extends AbstractTemplateEngine {
    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {

    }

    @Override
    public String templateFilePath(String filePath) {
        return null;
    }
}
