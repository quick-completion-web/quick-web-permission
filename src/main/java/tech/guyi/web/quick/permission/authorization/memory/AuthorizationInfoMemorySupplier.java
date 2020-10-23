package tech.guyi.web.quick.permission.authorization.memory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import tech.guyi.web.quick.permission.configuration.PermissionConfiguration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AuthorizationInfoMemorySupplier implements InitializingBean {

    @Resource
    private ApplicationContext context;
    private final Map<String, AuthorizationInfoMemory> memories = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        this.context.getBeansOfType(AuthorizationInfoMemory.class)
                .values()
                .forEach(memory -> memories.put(memory.forType(),memory));
    }

    @Resource
    private PermissionConfiguration configuration;

    public AuthorizationInfoMemory getMemory(String type){
        return this.memories.get(type);
    }

    public AuthorizationInfoMemory getMemory(){
        return Optional.ofNullable(this.getMemory(configuration.getAuthorization().getMemory()))
                .orElseThrow(() -> new RuntimeException(String.format("找不到授权信息存储器 [%s]",configuration.getAuthorization().getMemory())));
    }


}
