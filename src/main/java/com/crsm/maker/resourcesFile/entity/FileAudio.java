package com.crsm.maker.resourcesFile.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 音乐表
 * </p>
 *
 * @author Ccr
 * @since 2019-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileAudio implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 资源表ID
     */
    private Integer resourceId;

    /**
     * 艺人名称
     */
    private String audioArtist;

    /**
     * 封面（资源表ID）
     */
    private Integer audioCoverId;

    /**
     * 歌词（资源表ID）
     */
    private Integer audioLrcId;

    /**
     * 创建时间（资源表ID）
     */
    private LocalDateTime audioCreate;

    /**
     * 更新时间（资源表ID）
     */
    private LocalDateTime audioUpdate;

    /**
     * 音乐名称
     */
    private String audioName;

    /**
     * 音频信息
     */
    @TableField(exist = false)
    private String audioPath;

    /**
     * 封面信息
     */
    @TableField(exist = false)
    private String coverPath;

    /**
     * 歌词信息
     */
    @TableField(exist = false)
    private String lrcPath;


}
