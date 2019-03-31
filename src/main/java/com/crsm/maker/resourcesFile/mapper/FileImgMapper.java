package com.crsm.maker.resourcesFile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crsm.maker.resourcesFile.entity.FileImg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ccr
 * @since 2019-03-27
 */
@Mapper
@Component
public interface FileImgMapper extends BaseMapper<FileImg> {

}
