package com.crsm.maker.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crsm.maker.user.entity.SysTree;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 权限树结构 Mapper 接口
 * </p>
 *
 * @author Ccr
 * @since 2019-01-14
 */
@Component
@Mapper
public interface SysTreeMapper extends BaseMapper<SysTree> {

    public List<SysTree> getMenuData();

}
