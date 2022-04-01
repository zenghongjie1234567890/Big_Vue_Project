package com.zhj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhj.entity.SysUser;
import com.zhj.mapper.SysUserMapper;
import com.zhj.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhj
 * @since 2022-03-28
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper mapper;

    @Override
    public Map<String,Object> login(SysUser sysUser) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<SysUser> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("username",sysUser.getUsername());
        SysUser user = mapper.selectOne(objectQueryWrapper);
        if (user==null){
            map.put("msg","该账号不存在！！请点击注册");
        }else {
            objectQueryWrapper.eq("password",sysUser.getPassword());
            SysUser user1 = mapper.selectOne(objectQueryWrapper);
            if (user1==null){
                map.put("msg","密码输入错误，请重新尝试！！");
            }else {
                map.put("msg",true);
            }
        }
        return map;
    }
}
