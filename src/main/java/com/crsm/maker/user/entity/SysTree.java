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
 * 权限树结构
 * </p>
 *
 * @author Ccr
 * @since 2019-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysTree implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父节点id=sys_rms表id
     */
    private Integer fRmsId;

    /**
     * 子节点id=sys_rms表id
     */
    private Integer rmsId;

    /**
     * 类型（0：权限菜单、1：按钮）
     */
    private Integer type;


    @TableField(exist = false)
    private SysRms sysRms;
}
