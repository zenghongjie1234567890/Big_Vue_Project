package com.zhj.big_vue_project;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.zhj.entity.SysUser;
import com.zhj.mapper.SysUserMapper;
import com.zhj.service.ISysUserService;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class BigVueProjectApplicationTests {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ISysUserService iSysUserService;

    @Test
    void contextLoads() {
        LinkedList<SysUser> list = new LinkedList<>();
        for (int i = 1; i < 10000; i++) {
            list.add(new SysUser(i, "23", "4534", "32", "543", "5534534543", "ewr", null));
        }
        iSysUserService.saveBatch(list);
    }

    // 导出操作测试
    @Test
    public void test() {
        List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
        List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");
        List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);
        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("C:/Users/zenghongjie/Desktop/a.xlsx");
        //通过构造方法创建writer
        //ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");
        //跳过当前行，既第一行，非必须，在此演示用
        //        writer.passCurrentRow();
        //合并单元格后的标题行，使用默认标题样式
                writer.merge(row1.size()-1 , "测试标题");
        //一次性写出内容，强制输出标题
                writer.write(rows, true);
        //关闭writer，释放内存
                writer.close();
    }

    // 导入操作测试
    @Test
    public void test01(){
        ExcelReader reader = ExcelUtil.getReader("C:\\Users\\zenghongjie\\Desktop\\b.xlsx");
        List<SysUser> read = reader.read(0, 1,3, SysUser.class);
        for (SysUser obj:
             read) {
            System.out.println(obj);
        }
        reader.close();
    }
}
