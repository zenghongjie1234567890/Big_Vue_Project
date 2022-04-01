package com.zhj.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Big_Vue_Project
 *
 * @author : 曾小杰
 * @date : 2022-04-01 21:17
 **/
@RestController
@RequestMapping("file")
public class FileController {

    @PostMapping("/jj")
    public String upload(){
        return null;
    }

}
