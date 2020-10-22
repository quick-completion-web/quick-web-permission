package tech.guyi.web.quick.permission.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.guyi.web.quick.permission.QuickPermissionCoreFilter;

import javax.annotation.Resource;

/**
 * @author guyi
 * 拦截器配置
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Resource
    private QuickPermissionCoreFilter filter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.filter).addPathPatterns("/**");
    }

}
