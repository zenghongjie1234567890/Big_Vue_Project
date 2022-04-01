package com.zhj.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhj.entity.SysUser;
import com.zhj.service.ISysUserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhj
 * @since 2022-03-28
 */
@RestController
@RequestMapping("user")
public class SysUserController {

    @Autowired
    ISysUserService iSysUserService;

    // 新增
    @PostMapping("adduser")
    public Map<String, Object> addUser(@RequestBody SysUser sysUser) {
        HashMap<String, Object> map = new HashMap<>();
        List<SysUser> list = iSysUserService.list();
        sysUser.setId(list.size() + 1);
        boolean b = false;
        try {
            b = iSysUserService.save(sysUser);
            map.put("msg", b);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", b);
        }
        return map;
    }

    // 登录界面
    @PostMapping("loginuser")
    public Map<String, Object> loginUser(@RequestBody SysUser sysUser) {
            Map<String, Object> map = iSysUserService.login(sysUser);
        return map;
    }

    // 注册页面
    @PostMapping("registuser")
    public Map<String, Object> registUser(@RequestBody SysUser sysUser) {
        List<SysUser> list = iSysUserService.list();
        sysUser.setId(list.size() + 1);
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",sysUser.getUsername());
        if (iSysUserService.getOne(wrapper)!=null){
            map.put("msg","，已经存在，注册失败！！");
        }else {
            boolean b = iSysUserService.save(sysUser);
            map.put("msg",b);
        }
        return map;
    }

    // 更新
    @PostMapping("updateUser")
    public Map<String, Object> updateUser(@RequestBody SysUser sysUser) {
        HashMap<String, Object> map = new HashMap<>();
        boolean update = iSysUserService.updateById(sysUser);
        map.put("msg", update);
        return map;
    }

    // 删除
    @GetMapping("delUser/{id}")
    public Map<String, Object> delUser(@PathVariable("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        boolean b = iSysUserService.removeById(id);
        return map;
    }

    // 批量删除
    @PostMapping("delmore")
    public Map<String, Object> delUser(@RequestBody List<Integer> ids) {
        HashMap<String, Object> map = new HashMap<>();
        boolean b = iSysUserService.removeByIds(ids);
        return map;
    }

    // 导出数据
    @GetMapping("export")
    public void export(HttpServletResponse response) throws IOException {
        // 查询出所有数据
        List<SysUser> list = iSysUserService.list();
        // 在内存中操作，写到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //// 可以自定义表头的别名
        //writer.addHeaderAlias("username","用户名");
        // 一次性写出list内的对象到excel,使用默认样式，强制输出标题
        writer.write(list, true);
        String fileName = "员工信息表.xlsx";// 文件名
        // 设置浏览器响应格式
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        outputStream.close();
        writer.close();
    }

    // 导入数据
    @PostMapping("import")
    public boolean importData(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<SysUser> sysUsers = reader.readAll(SysUser.class);
        boolean b = iSysUserService.saveBatch(sysUsers);
        return b;
    }

    // 分页
    @GetMapping("page")
    public Map<String, Object> findPage(@RequestParam Integer pagenum,
                                        @RequestParam Integer pagesize,
                                        @RequestParam(defaultValue = "") String username) {
        IPage<SysUser> page = new Page<>(pagenum, pagesize);
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.like("username", username);
        IPage<SysUser> iPage = iSysUserService.page(page, wrapper);
        HashMap<String, Object> map = new HashMap<>();
        long total = iPage.getTotal();
        map.put("data", iPage);
        return map;
    }
}
