package com.ldd.springboot.controller;


import com.ldd.springboot.entity.User;
import com.ldd.springboot.service.UserService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  用户表 前端控制器
 * </p>
 *
 * @author liujian
 * @since 2019-04-09
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public  void  addUser(User user){
        try {
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            user.setSalt(salt);
            user.setUserName("admin");
            String encodedPassword = new SimpleHash("md5", user.getPassword(), salt, 2).toString();
            user.setPassword(encodedPassword);
            userService.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
