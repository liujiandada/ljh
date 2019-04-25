package com.ldd.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("ROLE_ID")
    private Long roleId;

    /**
     * 角色名
     */
    @TableField("ROLE_NAME")
    private String roleName;

    /**
     * 角色描述
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 角色是否分配（0-是,1-否）
     */
    @TableField("AVAILABLE")
    private Integer available;

    /**
     * 角色资源集合
     */
    @TableField(exist = false)
    private List<SysPermission> sysPermissionList ;


}
