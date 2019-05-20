package com.ldd.springboot.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.springboot.entity.SysRole;
import com.ldd.springboot.entity.User;
import com.ldd.springboot.mapper.SysRoleMapper;
import com.ldd.springboot.mapper.UserMapper;
import com.ldd.springboot.result.Result;
import com.ldd.springboot.result.ResultFactory;
import com.ldd.springboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Resource
    SysRoleMapper sysRoleMapper;
    /**
     * 根据userId获取用户角色
     * @param userId
     * @return
     */
    @RequestMapping(value = "/api/user/getUserRole", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserRole(Long userId){
        try {
            List<SysRole> roleList =sysRoleMapper.listSysRoleByRoleId(userId);
            if(roleList!=null && !"".equals(roleList)){
                return ResultFactory.buildSuccessResult(roleList);
            }
            return ResultFactory.buildSuccessResult("用户无角色信息");
        }catch (Exception e){
            e.printStackTrace();
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/api/user/updatePwd", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePwd(String oldPassword , String newPassword){
        try {
            Subject subject = SecurityUtils.getSubject();
            User user = (User)subject.getPrincipal();
            String Password = new SimpleHash("md5", oldPassword, user.getSalt(), 2).toString();
            if(Password.equals(user.getPassword())){
                String salt = new SecureRandomNumberGenerator().nextBytes().toString();
                String encodedPassword = new SimpleHash("md5", newPassword, salt, 2).toString();
                user.setSalt(salt);
                user.setPassword(encodedPassword);
                boolean bol=userService.updateById(user);
                if(bol){
                    return ResultFactory.buildSuccessResult("修改成功");
                }
                    return ResultFactory.buildSuccessResult("修改失败");
            }
            return ResultFactory.buildSuccessResult("密码错误");
        }catch (Exception e){
            e.printStackTrace();
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    /**
     * 获取个人信息
     * @return
     */
    @RequestMapping(value = "/api/user/getPersonalInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result getPersonalInfo(){
        try {
            Subject subject = SecurityUtils.getSubject();
            User user = (User)subject.getPrincipal();
            user = userService.findByUserName(user.getUserName());
            return ResultFactory.buildSuccessResult(user);
        }catch (Exception e){
            e.printStackTrace();
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    /**
     * 修改个人信息
     * @return
     */
    @RequestMapping(value = "/api/user/updatePersonalInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePersonalInfo( String userName,String userNum ,String sex ,String telephone){
        try {
            Subject subject = SecurityUtils.getSubject();
            User user = (User)subject.getPrincipal();
            user.setUserName(userName);
            user.setUserNum(userNum);
            user.setSex(sex);
            user.setTelephone(telephone);
           boolean bol = userService.updateById(user);
           if(bol){
               return ResultFactory.buildSuccessResult("修改成功");
           }
            return ResultFactory.buildSuccessResult("修改失败");
        }catch (Exception e){
            e.printStackTrace();
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    /**
     * 查询所有用户
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    @RequestMapping(value = "/api/user/list", method = RequestMethod.POST)
    @ResponseBody
    public Result listUser(int pageNum, int pageSize, boolean isSearchCount, Map map){
        try {
            Page<User> page = new Page<User>(pageNum, pageSize,isSearchCount);
            IPage <User> iPage = userService.selectALL( page, map);
            return ResultFactory.buildSuccessResult(iPage);
        }catch (Exception e){
            e.printStackTrace();
            return ResultFactory.buildFailResult(e.getMessage());
        }
}

}
