package com.ldd.springboot.login;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.ldd.springboot.entity.User;
import com.ldd.springboot.entity.vo.VueLoginInfoVo;
import com.ldd.springboot.result.Result;
import com.ldd.springboot.result.ResultFactory;
import com.ldd.springboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class LoginController {

    @Autowired
    UserService userService;

    /**
     * 登录控制器，前后端分离用的不同协议和端口，所以需要加入@CrossOrigin支持跨域。
     * 给VueLoginInfoVo对象加入@Valid注解，并在参数中加入BindingResult来获取错误信息。
     * 在逻辑处理中我们判断BindingResult知否含有错误信息，如果有错误信息，则直接返回错误信息。
     */
    @CrossOrigin
    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Result login(@Valid @RequestBody VueLoginInfoVo loginInfoVo) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginInfoVo.getUsername(), loginInfoVo.getPassword());
        try {
            subject.login(token);
            return ResultFactory.buildSuccessResult("登陆成功");
        } catch (IncorrectCredentialsException e) {
            return ResultFactory.buildFailResult("密码错误");
        } catch (LockedAccountException e) {
//            jsonObject.put("msg", "登录失败，该用户已被冻结");
        } catch (AuthenticationException e) {
//            jsonObject.put("msg", "该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultFactory.buidResult(500,"err","登陆失败");
    }


    @RequestMapping( value = "/login", method = RequestMethod.POST ,produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Result login(HttpServletRequest request,@Valid  @RequestBody VueLoginInfoVo loginInfoVo){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginInfoVo.getUsername(), loginInfoVo.getPassword());

        try {
            subject.login(token);
            User user =(User) subject.getPrincipal();
            user=userService.findByUserId(user.getUserId());
            return ResultFactory.buildSuccessResult(user);
        } catch (IncorrectCredentialsException e) {
            return ResultFactory.buildFailResult("密码错误");
        } catch (LockedAccountException e) {
            return ResultFactory.buildFailResult("用户禁止登录");
        } catch (AuthenticationException e) {
            return ResultFactory.buildFailResult("用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultFactory.buidResult(500,"登陆失败","null");

    }
    /**
     * 未登录，返回未登录状态信息由前端控制跳转页面
     * @return
     */
    @RequestMapping(value = "/unauth")
    @ResponseBody
    public Result unauth(ServletRequest request, ServletResponse response) {
       String code= (String) request.getAttribute("code");
       switch (code){
           case "001":
               return ResultFactory.buildFailResult("未登录");
           case "002":
               return ResultFactory.buildFailResult("无权访问");
       }
        return ResultFactory.buildFailResult("内部错误");
    }
}
