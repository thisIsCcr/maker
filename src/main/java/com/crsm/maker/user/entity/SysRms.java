package com.crsm.maker.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author Ccr
 * @since 2019-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRms implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限—id
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限标识
     */
    private String rmsName;

    /**
     * 图标
     */
    private String rmsIocn;

    /**
     * 请求地址
     */
    private String rmsUrl;


}
