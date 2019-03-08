package com.crsm.maker.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 角色权限关联表
 * </p>
 *
 * @author Ccr
 * @since 2019-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleRms implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色权限关联
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色表主键
     */
    private Integer roleId;

    /**
     * 权限表主键
     */
    private Integer rmsId;

    /**
     * 映射值
     */
    @TableField(exist = false)
    private SysRms sysRms;
}
