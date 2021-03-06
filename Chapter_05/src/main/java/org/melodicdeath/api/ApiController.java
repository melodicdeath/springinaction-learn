package org.melodicdeath.api;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.*;
import com.google.common.base.Optional;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.schema.property.ModelProperty;
import springfox.documentation.service.AllowableValues;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelBuilderPlugin;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.TypeNameProviderPlugin;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.*;
import springfox.documentation.spi.service.contexts.*;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.*;
import java.lang.reflect.AnnotatedElement;
import java.sql.Timestamp;

@RestController
@RequestMapping("/api")
@Validated
@Api(value = "测试api", tags = "tags1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, description = "desc")
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

    @ApiOperation(value = "test swagger response", extensions =
            {@Extension(properties = @ExtensionProperty(name = "data", value = "Data.class"))})
    @GetMapping("/say4")
    @ApiModelPropertyReplaces(value = "data", targetType = Data.class)
    @ApiResponses(@ApiResponse(code = 200, message = "OK.",reference = "Data"))
    public Message say4(@VersionApi int version) {
        Message<Data> message = new Message<>(Data.class);
        message.setData(new Data());

        return message;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @Documented
    @interface ApiModelPropertyReplaces {
        String value() default "";

        Class<?> targetType() default Void.class;
    }

    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface VersionApi {
    }


    @Component
    @Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1000)
    class TypeReplace implements ModelPropertyBuilderPlugin,ModelBuilderPlugin {
        private TypeResolver resolver;

        public TypeReplace(TypeResolver resolver) {
            this.resolver = resolver;
        }

        @Override
        public void apply(ModelPropertyContext context) {
            BeanPropertyDefinition beanPropertyDefinition = context.getBeanPropertyDefinition().get();
//            ((AnnotatedClass) ((POJOPropertyBuilder) beanPropertyDefinition)._fields.value._typeContext)._fields
//            for(AnnotatedMethod method : ((AnnotatedClass)beanPropertyDefinition.getField().getTypeContext()).memberMethods()){
//                if(method.getName().equals("getClazz")){
//                    try {
//                        method.fixAccess(true);
//                        System.out.println(method.call());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
            if (beanPropertyDefinition.getName().equals("data")) {
                context.getBuilder().type(resolver.resolve(Data.class)).build();
            }

        }

        @Override
        public boolean supports(DocumentationType delimiter) {
            return true;
        }

        @Override
        public void apply(ModelContext context) {

        }

//        @Override
//        public void apply(OperationContext context) {
//            apiModelPropertyReplacesOptional = context.findAnnotation(ApiModelPropertyReplaces.class);
//        }

//        @Override
//        public void apply(RequestMappingContext context) {
//            Optional<ApiController.ApiModelPropertyReplaces> apiModelPropertyReplacesOptional
//                    = context.findAnnotation(ApiModelPropertyReplaces.class);
//            int i = 0;
//        }

    }

    @Component
    @Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1000)
    class VersionApiReader implements ParameterBuilderPlugin {
        private TypeResolver resolver;

        public VersionApiReader(TypeResolver resolver) {
            this.resolver = resolver;
        }

        @Override
        public void apply(ParameterContext parameterContext) {
            ResolvedMethodParameter methodParameter = parameterContext.resolvedMethodParameter();
            com.google.common.base.Optional<ApiController.VersionApi> requestParam = methodParameter.findAnnotation(ApiController.VersionApi.class);
            if (requestParam.isPresent()) {
                parameterContext.parameterBuilder()
                        .parameterType("header")
                        .name("v")
                        .type(resolver.resolve(String.class));
            }
        }

        @Override
        public boolean supports(DocumentationType documentationType) {
            return true;
        }
    }

    @ApiModel(description = "data")
    class Data {
        private int id;
        private String name1;
        @ApiModelProperty(dataType = "java.lang.String", example = "2014-10-10 01:00:00", required = true)
        private Timestamp created;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName1() {
            return name1;
        }

        public void setName1(String name1) {
            this.name1 = name1;
        }

        public Timestamp getCreated() {
            return created;
        }

        public void setCreated(Timestamp created) {
            this.created = created;
        }
    }

    @ApiModel(description = "消息对象消息对象消息对象")
    class Message<T> {
        @ApiModelProperty(value = "id", required = true)
        private int code;
        @ApiModelProperty(value = "消息", required = true)
        private String msg;
        @ApiModelProperty("数据")
        private T data;

        private Class<T> clazz;

        Message(Class<T> clazz) {
            this.clazz = clazz;
        }

        public Class<T> getClazz() {
            return clazz;
        }

        public void setClazz(Class<T> clazz) {
            this.clazz = clazz;
        }

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

        public T getData() {
            return data;
        }

        public void setData(T data) {
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
