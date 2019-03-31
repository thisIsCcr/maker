package com.crsm.maker.resourcesFile.mapper;

import com.crsm.maker.resourcesFile.entity.FileAudio;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 音乐表 Mapper 接口
 * </p>
 *
 * @author Ccr
 * @since 2019-03-28
 */
@Component
@Mapper
public interface FileAudioMapper extends BaseMapper<FileAudio> {

     List<FileAudio> getAllAudioInfo();

}
