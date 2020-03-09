package com.sinothk.cloud.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/index.sn")
    public String test() {
        return "index";
    }

    @RequestMapping("/addFiles.sn")
    public String muti_file() {
        return "addFiles";
    }
}
