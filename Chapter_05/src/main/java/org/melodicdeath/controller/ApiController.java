package org.melodicdeath.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

//    @GetMapping("/say/{_name}")
    @RequestMapping(value = "/say/{name}",method = RequestMethod.GET)
    @ResponseBody
    public String say(@PathVariable String name) {
        return name;
    }
}
