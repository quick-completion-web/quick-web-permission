package tech.guyi.web.quick.permission.authorization.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.guyi.web.quick.permission.authorization.AuthorizationCurrent;
import tech.guyi.web.quick.permission.authorization.memory.AuthorizationInfoMemorySupplier;
import tech.guyi.web.quick.permission.authorization.memory.defaults.MapAuthorizationInfoMemory;

@Configuration
public class AuthorizationAutoConfiguration {

    @Bean
    @ConfigurationProperties("tech.guyi.web.quick.permission.memory")
    public AuthorizationConfiguration authorizationConfiguration(){
        return new AuthorizationConfiguration();
    }

    @Bean
    public AuthorizationInfoMemorySupplier authorizationInfoMemorySupplier(){
        return new AuthorizationInfoMemorySupplier();
    }

    @Bean
    @ConditionalOnMissingBean(MapAuthorizationInfoMemory.class)
    public MapAuthorizationInfoMemory mapAuthorizationInfoMemory(){
        return new MapAuthorizationInfoMemory();
    }

    @Bean
    public AuthorizationCurrent authorizationCurrent(){
        return new AuthorizationCurrent();
    }

}
