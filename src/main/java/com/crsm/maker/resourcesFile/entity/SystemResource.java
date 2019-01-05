package com.crsm.maker.resourcesFile.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统资源表
 * </p>
 *
 * @author Ccr
 * @since 2018-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemResource implements Serializable {

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
    private String resType;

    /**
     * 是否可用
     */
    @TableField("res_isStop")
    private Integer resIsstop;

    /**
     * 创建时间
     */
    @TableField("res_createTime")
    private LocalDate resCreatetime;

    /**
     * 更新时间
     */
    @TableField("res_updateTime")
    private LocalDate resUpdatetime;

    /**
     * 上传人
     */
    private Integer userId;


}
