package tech.guyi.web.quick.permission.mapping.register.entry;

import org.springframework.util.StringUtils;
import tech.guyi.web.quick.permission.annotation.Authorization;
import tech.guyi.web.quick.permission.mapping.entry.Mapping;
import tech.guyi.web.quick.permission.mapping.register.MappingRegister;

import java.util.Optional;

/**
 * @author guyi
 * 地址映射生成器
 */
public class MappingBuilder {

    private final MappingRegister register;
    private final boolean authorization;
    private String pattern;
    private String method;
    private String type;

    public MappingBuilder(MappingRegister register, boolean authorization) {
        this.register = register;
        this.authorization = authorization;
    }

    public MappingBuilder method(String method){
        if (StringUtils.isEmpty(this.method) || "*".equals(method) || ".*".equals(method)){
            this.method = method.toLowerCase();
        }else{
            if (!"*".equals(this.method)){
                this.method = this.method + "|" + method.toLowerCase();
            }
        }
        return this;
    }

    public MappingBuilder pathPattern(String pattern){
        this.pattern = pattern;
        return this;
    }

    public MappingBuilder type(String type){
        this.type = type;
        return this;
    }

    public MappingRegister end(){
        Mapping mapping = new Mapping(
                Optional.ofNullable(this.pattern).orElse("/**"),
                Optional.ofNullable(this.method).filter(method -> !method.equals("*")).orElse(".*"),
                Optional.ofNullable(this.type).orElse(Authorization.DEFAULT_TYPE),
                this.authorization
        );
        return this.register.register(mapping);
    }

}

