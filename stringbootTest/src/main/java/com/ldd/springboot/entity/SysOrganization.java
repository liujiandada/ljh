package com.ldd.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 组织机构表
 * </p>
 *
 * @author liujian
 * @since 2019-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("ORG_ID")
    private Long orgId;

    /**
     * 组织机构名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 排序号
     */
    @TableField("SORT_NO")
    private Integer sortNo;

    /**
     * 父编号ID
     */
    @TableField("PARENT_ID")
    private Long parentId;

    /**
     * 父排序号列表
     */
    @TableField("PARENT_IDS")
    private String parentIds;

    /**
     * 是否可用（0-是，1-否）
     */
    @TableField("AVAILABLE")
    private Integer available;


}
