package com.ldd.springboot.login;

import com.ldd.springboot.entity.User;
import com.ldd.springboot.result.Result;
import com.ldd.springboot.result.ResultFactory;
import com.ldd.springboot.service.SysPermissionService;
import com.ldd.springboot.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;


    /**
     * 注册功能
     * @param user
     * @return
     */
    @RequestMapping(value = "/api/Register", method = RequestMethod.POST)
    @ResponseBody
    public Result addUser(User user){
        try {

            User users = userService.findByUserName(user.getUserName());
            if(users!=null){
                return ResultFactory.buildSuccessResult("用户名已存在");
            }
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            user.setSalt(salt);
            String encodedPassword = new SimpleHash("md5", user.getPassword(), salt, 2).toString();
            user.setPassword(encodedPassword);
            user.setCreateTime(LocalDateTime.now());
            boolean bool= userService.save(user);
            if(bool){
                return ResultFactory.buildSuccessResult(user.getUserName());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultFactory.buildFailResult(e.getMessage());
        }
        return ResultFactory.buildSuccessResult("注册失败");
    }

    /**
     * 用户名查重
     * @param userName
     * @return
     */
    @RequestMapping(value = "/api/userName", method = RequestMethod.POST)
    @ResponseBody
    public Result existenceUserName(String userName){
        try {
            User user = userService.findByUserName(userName);
            if(user!=null){
                return ResultFactory.buildSuccessResult("用户名已存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultFactory.buildFailResult(e.getMessage());
        }
        return ResultFactory.buildSuccessResult("成功");
    }
}
