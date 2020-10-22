package tech.guyi.web.quick.permission.mapping.register;

/**
 * @author guyi
 * 映射配置
 * 实现此接口, 用于手动注册地址映射
 */
public interface MappingManagerConfiguration {

    /**
     * 配置映射
     * @param register 映射注册器
     */
    void configure(MappingRegister register);

}
