package tech.guyi.web.quick.permission.mapping.register;

import tech.guyi.web.quick.permission.annotation.Authorization;
import tech.guyi.web.quick.permission.mapping.MappingRepository;
import tech.guyi.web.quick.permission.mapping.entry.Mapping;
import tech.guyi.web.quick.permission.mapping.register.entry.MappingBuilder;

/**
 * @author guyi
 * 映射注册器
 */
public class MappingRegister {

    private final MappingRepository repository;
    public MappingRegister(MappingRepository repository) {
        this.repository = repository;
    }

    /**
     * 创建不需要授权的地址映射创建器
     * @return 映射创建器
     */
    public MappingBuilder allow(){
        return new MappingBuilder(this,false);
    }

    /**
     * 添加不需要授权的地址映射
     * @param pathPattern 地址匹配
     * @return
     */
    public MappingRegister allow(String pathPattern){
        return this.register(new Mapping(pathPattern, ".*", Authorization.DEFAULT_TYPE, false));
    }

    /**
     * 创建需要授权的地址映射创建器
     * @return 映射创建器
     */
    public MappingBuilder authority(){
        return new MappingBuilder(this,true);
    }

    /**
     * 添加需要授权的地址映射
     * @param pathPattern 地址匹配
     * @return
     */
    public MappingRegister authority(String pathPattern){
        return this.register(new Mapping(pathPattern, ".*", Authorization.DEFAULT_TYPE));
    }

    public MappingRegister register(Mapping mapping){
        this.repository.add(mapping);
        return this;
    }

}
