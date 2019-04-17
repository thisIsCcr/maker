package com.crsm.maker.resourcesFile.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.crsm.maker.base.BaseController;
import com.crsm.maker.resourcesFile.entity.FileAudio;
import com.crsm.maker.resourcesFile.entity.SysResource;
import com.crsm.maker.resourcesFile.service.IFileAudioService;
import com.crsm.maker.resourcesFile.service.ISystemResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 音乐表 前端控制器
 * </p>
 *
 * @author Ccr
 * @since 2019-03-28
 */
@RestController
@RequestMapping("/fileAudio")
public class FileAudioController extends BaseController {

    @Autowired
    IFileAudioService iFileAudioService;

    @Autowired
    ISystemResourceService iSystemResourceService;


    /**
     * 添加音乐
     */
    @PostMapping("/addMusic")
    public String addMusic(@RequestBody FileAudio data) {
        data.setAudioCreate(LocalDateTime.now());
        System.out.println(data.toString());
        boolean result = iFileAudioService.save(data);
        if (result) {
            return success();
        } else {
            return fail();
        }
    }

    /**
     * 获取音乐列表
     *
     * @return
     */
    @GetMapping("getMusicList")
    public String getMusicList() {
        List list = iFileAudioService.getMusicList(Wrappers.lambdaQuery());
        return success(list);
    }


    /**
     * 获取音乐信息
     *
     * @return
     */
    @GetMapping("/getAllMusicInfo")
    public String getAllMusicInfo() {
        List list = iFileAudioService.getAllAudioInfo();
        return success(list);
    }

    /**
     * 获取所有未添加的lrc文件信息
     *
     * @return
     */
    @GetMapping("/getAllLrcFileInfo")
    public String getAllLrcFileInfo(@RequestParam(value = "id", required = false) Integer id) {
        String notExists = id == null
                ? "SELECT audio_lrc_id FROM file_audio fa WHERE fa.`audio_lrc_id`=sr.`id`"
                : "SELECT audio_lrc_id FROM file_audio fa WHERE fa.`audio_lrc_id`=sr.`id` and fa.id!=" + id;
        LambdaQueryWrapper wrapper = Wrappers.<SysResource>lambdaQuery()
                .notExists(notExists)
                .likeLeft(SysResource::getResName, ".lrc");
        List list = iSystemResourceService.getAllMusicInfo(wrapper);
        return success(list);
    }

    /**
     * 获取音频信息
     *
     * @return
     */
    @GetMapping("/getAllAudioFileInfo")
    public String getAllAudioFileInfo(@RequestParam(value = "id", required = false) Integer id) {
        String notExists = id == null
                ? "SELECT `resource_id` FROM file_audio"
                : "SELECT `resource_id` FROM file_audio where id!=" + id;
        List list = iSystemResourceService.list(new QueryWrapper<SysResource>().lambda()
                .eq(SysResource::getResType, "0")
                .notInSql(SysResource::getId, notExists));
        return success(list);
    }

    /**
     * 获取修改信息
     * @param id
     * @return
     */
    @GetMapping("getEditMusicInfo/{id}")
    public String getEditMusicInfo(@PathVariable("id") Integer id) {
        FileAudio fileAudio = iFileAudioService.getEditMusicInfo(Wrappers.query().eq("fa.id", id));
        return success(fileAudio);
    }


    @PostMapping("updateMusic")
    public String updateMusic(@RequestBody FileAudio fileAudio){
        fileAudio.setAudioUpdate(LocalDateTime.now());
        iFileAudioService.update(fileAudio,Wrappers.<FileAudio>lambdaUpdate().eq(FileAudio::getId,fileAudio.getId()));
        return success();
    }
}
