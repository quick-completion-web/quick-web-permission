package tech.guyi.web.quick.permission.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.guyi.web.quick.permission.QuickPermissionCoreFilter;
import tech.guyi.web.quick.permission.handler.AuthorizationHandler;
import tech.guyi.web.quick.permission.handler.AuthorizationHandlerRepository;
import tech.guyi.web.quick.permission.handler.defaults.DefaultAuthorizationHandler;
import tech.guyi.web.quick.permission.mapping.MappingMatcher;
import tech.guyi.web.quick.permission.mapping.MappingRepository;
import tech.guyi.web.quick.permission.mapping.register.DefaultMappingManagerConfiguration;
import tech.guyi.web.quick.permission.mapping.register.MappingManagerConfiguration;

/**
 * @author guyi
 * 自动配置
 */
@Configuration
public class AutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "tech.guyi.web.quick.permission")
    public PermissionConfiguration permissionConfiguration(){
        return new PermissionConfiguration();
    }

    @Bean
    public QuickPermissionCoreFilter quickPermissionCoreFilter(){
        return new QuickPermissionCoreFilter();
    }

    @Bean
    public MappingMatcher mappingMatcher(){
        return new MappingMatcher();
    }

    @Bean
    public MappingRepository mappingRepository(){
        return new MappingRepository();
    }

    @Bean
    public AuthorizationHandlerRepository authorizationHandlerRepository(){
        return new AuthorizationHandlerRepository();
    }

    @Bean
    @ConditionalOnMissingBean(AuthorizationHandler.class)
    public DefaultAuthorizationHandler defaultAuthorizationHandler(){
        return new DefaultAuthorizationHandler();
    }

    @Bean
    @ConditionalOnMissingBean(MappingManagerConfiguration.class)
    public DefaultMappingManagerConfiguration defaultMappingManagerConfiguration(){
        return new DefaultMappingManagerConfiguration();
    }

}
