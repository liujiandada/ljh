package com.ldd.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
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
    private Long userId;

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
     * 组织机构ID（0为无组织用户）
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 组织机构名称（0为无组织用户）
     */
    @TableField(exist = false)
    private String orgName;

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
     * 注册时间
     */
    @TableField("CREATE_TIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 是否锁定
     */
    @TableField("LOCKED")
    private Integer locked;

    /**
     * 用户角色集合
     */
    @TableField(exist = false)
    private List<SysRole> roleList ;

    /**
     * 用户资源集合
     */
    @TableField(exist = false)
    private List<SysPermission> sysPermissionList ;

    /**
     * 用户菜单集合
     */
    @TableField(exist = false)
    private List<SysPermission> authMenu;

    /**
     * 用户权限集合
     */
    @TableField(exist = false)
    private Set<String> permissions;

}
