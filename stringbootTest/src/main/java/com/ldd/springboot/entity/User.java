package com.ldd.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author liujian
 * @since 2019-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("USER_ID")
    private Integer userId;

    /**
     * 账号
     */
    @TableField("USER_NUM")
    private String userNum;

    /**
     * 用户名
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 密码盐
     */
    @TableField("SALT")
    private String salt;

    /**
     * 主角色
     */
    @TableField("ROLE_ID")
    private Integer roleId;

    /**
     * 辅助角色ID串
     */
    @TableField("ASSIST_ROLES")
    private String assistRoles;

    /**
     * 登录权限（0-不限制,1-APP）
     */
    @TableField("LOG_POWER")
    private String logPower;

    /**
     * 性别(0-女,1-男)
     */
    @TableField("SEX")
    private String sex;

    /**
     * 手机号
     */
    @TableField("TELEPHONE")
    private String telephone;

    /**
     * 身份证号
     */
    @TableField("ID_CARD_NO")
    private String idCardNo;

    /**
     * 注册时间
     */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;


}
