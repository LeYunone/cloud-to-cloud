package com.leyunone.cloudcloud.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2024/2/27 14:27
 */
@RequestMapping("/")
@Controller
public class ViewController {

    @GetMapping("/")
    public String htmlView() {
        return "redirect:/cloudMapping.html";
    }
}
