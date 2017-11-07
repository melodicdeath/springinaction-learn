package org.melodicdeath.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
@Validated
@Api(value = "测试api")
public class ApiController {

    @ApiOperation(value = "test", notes = "notes")
    @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String")
    @GetMapping("/say/{name}")
    public @NotNull(message = "返回值不能为空")
    Message say(@PathVariable @Size(min = 1, max = 2,message = "长度必须为两个字符") String name) {
        if (name.length() == 1) {
            return null;
        } else {
            return new Message("草泥马");
        }
    }

    public class Message{
        String msg;

        Message(String msg) {
            this.msg = msg;
        }
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleValidationFailure(ConstraintViolationException ex) throws UnsupportedEncodingException {

        StringBuilder messages = new StringBuilder();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            messages.append(violation.getMessage() + "\n");
        }

        return messages.toString();
    }
}
