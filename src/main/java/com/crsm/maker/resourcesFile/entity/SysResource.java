package com.crsm.maker.resourcesFile.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统资源表
 * </p>
 *
 * @author Ccr
 * @since 2018-12-25
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysResource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统资源Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 资源名称
     */
    private String resName;

    /**
     * 资源路径
     */
    private String resPath;

    /**
     * 资源类型
     */
    private Integer resType;

    /**
     * 是否可用
     */
    @TableField("res_isStop")
    private Integer resIsstop;

    /**
     * 创建时间
     */
    @TableField("res_createTime")
    private LocalDateTime resCreatetime;

    /**
     * 更新时间
     */
    @TableField("res_updateTime")
    private LocalDateTime resUpdatetime;

    /**
     * 上传人
     */
    private Integer userId;


}
