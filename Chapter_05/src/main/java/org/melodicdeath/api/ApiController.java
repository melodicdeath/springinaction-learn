package org.melodicdeath.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

@RestController
@RequestMapping("/api")
@Validated
@Api(value = "测试api", tags = "tags1",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,description = "desc")
public class ApiController {

    @ApiOperation(value = "test", notes = "notes")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/say/{name}")
    public @NotNull(message = "返回值不能为空")
    String say(@PathVariable @Size(min = 1, max = 2, message = "长度必须为两个字符") String name) {
        if (name.length() == 1) {
            return null;
        } else {
            return "草泥马";
        }
    }

    @ApiOperation(value = "test json")
    @PostMapping(value = "/say2", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Message say2(@RequestBody Message message) {
        return message;
    }

    @ApiOperation(value = "test formurlencoded")
    @PostMapping(value = "/say3", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Message say3(@RequestBody @ModelAttribute Message message) {
        return message;
    }

    @ApiOperation("test swagger response")
    @GetMapping("/say4")
    @ApiResponses(@ApiResponse(code = 200,message = "OK.",response = Data.class))
    public Message say4(){
        Message message = new Message();
        message.setData(new Data());

        return message;
    }

    @ApiModel(description = "data")
    class Data{
        private int id;
        private String name;
        @ApiModelProperty(dataType = "java.lang.String",example = "2014-10-10 01:00:00",required = true)
        private Timestamp created;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Timestamp getCreated() {
            return created;
        }

        public void setCreated(Timestamp created) {
            this.created = created;
        }
    }

    @ApiModel(description = "消息对象消息对象消息对象")
    static class Message {
        @ApiModelProperty(value = "id", required = true)
        private int code;
        @ApiModelProperty(value = "消息", required = true)
        private String msg;
        @ApiModelProperty("数据")
        private Object data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
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
