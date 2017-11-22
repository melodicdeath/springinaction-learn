package org.melodicdeath.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zt.melody on 2017/11/2.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String Home(){
        return "home";
    }
}
