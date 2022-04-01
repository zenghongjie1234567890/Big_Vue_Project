package com.zhj.service;

import com.zhj.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhj
 * @since 2022-03-28
 */
public interface ISysUserService extends IService<SysUser> {
    Map<String,Object> login(SysUser sysUser);
}
