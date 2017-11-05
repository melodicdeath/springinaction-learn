package org.melodicdeath.api;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/say/{name}")
//    @RequestMapping(value = "/say/{name}", method = RequestMethod.GET)
//    @ResponseBody
    public String say(@PathVariable @Size(min = 1, max = 5) String name) {
        return name;
    }
}
