package com.leyunone.cloudcloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/3/6
 */
@RequestMapping("/")
@Controller
public class ViewController {

    @GetMapping("/")
    public String htmlView() {
        return "redirect:/cloudMapping.html";
    }
}