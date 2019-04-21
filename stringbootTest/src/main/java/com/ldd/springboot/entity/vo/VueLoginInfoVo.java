package com.ldd.springboot.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description Vue登录页面demo信息对象实体
 * @author WANGJIHONG
 * @date 2018年4月5日 下午10:57:53
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 备注信息
 */
@Data
public class VueLoginInfoVo {

    @NotNull(message="用户名不允许为空")
    private String username;

    @NotNull(message="密码不允许为空")
    private String password;

    /**
     * 记住密码
     */
    private boolean remember = false;


}
