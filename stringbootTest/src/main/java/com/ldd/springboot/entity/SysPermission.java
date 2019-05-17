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
 * 资源表
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("PERM_ID")
    private Long permId;

    /**
     * 资源名
     */
    @TableField("PERM_NAME")
    private String permName;

    /**
     * 资源类型[menu | button]
     */
    @TableField("RESOURCE_TYPE")
    private String resourceType;

    /**
     * 资源图标
     */
    @TableField("ICON")
    private String icon;

    /**
     * 资源路径
     */
    @TableField("URL")
    private String url;

    /**
     * 前端路由路径
     */
    @TableField("ROUTE_URL")
    private String routeUrl;

    /**
     * 权限字符串
     */
    @TableField("PERMISSION")
    private String permission;

    /**
     * 父编号
     */
    @TableField("PARENT_ID")
    private Long parentId;

    /**
     * 父编号列表
     */
    @TableField("PARENT_IDS")
    private String parentIds;

    /**
     * 排序号
     */
    @TableField("SORT_NO")
    private Integer sortNo;

    /**
     * 是否可分配（0-是，1-否）
     */
    @TableField("AVAILABLE")
    private Integer available;

    /**
     *
     */
    @TableField(exist = false)
    private List<SysPermission> child;


}
