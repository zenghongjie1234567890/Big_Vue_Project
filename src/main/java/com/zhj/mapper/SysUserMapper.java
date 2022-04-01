package com.zhj.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.zhj.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhj
 * @since 2022-03-28
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
