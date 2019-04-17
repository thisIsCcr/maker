package com.crsm.maker.resourcesFile.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crsm.maker.resourcesFile.entity.FileAudio;

import java.util.List;

/**
 * <p>
 * 音乐表 服务类
 * </p>
 *
 * @author Ccr
 * @since 2019-03-28
 */
public interface IFileAudioService extends IService<FileAudio> {

    List<FileAudio> getAllAudioInfo();

    List<FileAudio> getMusicList(Wrapper wrapper);

    FileAudio getEditMusicInfo(Wrapper wrapper);
}
