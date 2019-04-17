package com.crsm.maker.resourcesFile.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.crsm.maker.resourcesFile.entity.FileAudio;
import com.crsm.maker.resourcesFile.mapper.FileAudioMapper;
import com.crsm.maker.resourcesFile.service.IFileAudioService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 音乐表 服务实现类
 * </p>
 *
 * @author Ccr
 * @since 2019-03-28
 */
@Transactional
@Service
public class FileAudioServiceImpl extends ServiceImpl<FileAudioMapper, FileAudio> implements IFileAudioService {

    @Autowired
    FileAudioMapper fileAudioMapper;

    @Override
    public List<FileAudio> getAllAudioInfo() {
        return fileAudioMapper.getAllAudioInfo();
    }

    @Override
    public List<FileAudio> getMusicList(Wrapper wrapper) {
        return fileAudioMapper.getMusicList(wrapper);
    }

    @Override
    public FileAudio getEditMusicInfo(Wrapper wrapper) {
        return fileAudioMapper.getEditMusicInfo(wrapper);
    }
}
