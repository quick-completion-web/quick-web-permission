package tech.guyi.web.quick.permission.mapping;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import tech.guyi.web.quick.permission.annotation.Authorization;
import tech.guyi.web.quick.permission.mapping.entry.Mapping;
import tech.guyi.web.quick.permission.mapping.register.MappingManagerConfiguration;
import tech.guyi.web.quick.permission.mapping.register.MappingRegister;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author guyi
 * 请求映射仓库
 */
public class MappingRepository implements CommandLineRunner {

    @Resource
    private MappingMatcher matcher;
    @Resource
    private ApplicationContext context;

    @Override
    public void run(String... args) {
        RequestMappingHandlerMapping handlerMapping = this.context.getBean(RequestMappingHandlerMapping.class);
        handlerMapping.getHandlerMethods().entrySet().stream()
                .map(e ->
                        Optional.of(e.getValue().getMethod())
                                .map(method -> {
                                    Authorization authorization = AnnotationUtils.findAnnotation(method,Authorization.class);
                                    if (authorization == null){
                                        Class<?> bean = e.getValue().getBeanType();
                                        authorization = AnnotationUtils.findAnnotation(bean,Authorization.class);
                                    }
                                    return authorization;
                                })
                                .map(authorization -> e.getKey().getPatternsCondition().getPatterns()
                                        .stream()
                                        .map(pattern -> Arrays.stream(authorization.type())
                                                .map(type ->
                                                        this.createMappingFromRequestMappingInfo(
                                                                e.getKey(),
                                                                type,
                                                                pattern,
                                                                Optional.of(authorization.detail())
                                                                        .filter(detail -> !StringUtils.isEmpty(detail))
                                                                        .orElse(null)
                                                        )
                                                )
                                                .collect(Collectors.toList()))
                                        .flatMap(Collection::stream)
                                        .collect(Collectors.toList()))
                                .orElse(null))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .forEach(this.mappings::add);

        this.mappings = this.mappings.stream()
                .sorted(Comparator.comparingInt((Mapping mapping) -> mapping.getUrl().length()).reversed())
                .distinct()
                .collect(Collectors.toList());

        MappingRegister register = new MappingRegister(this);
        this.configurations.forEach(configurations -> configurations.configure(register));
        this.injections.forEach(injection -> injection.injection(this.mappings));
    }

    /**
     * 需要授权访问的路径
     */
    private List<Mapping> mappings = new LinkedList<>();

    private Mapping createMappingFromRequestMappingInfo(RequestMappingInfo info,String type,String pattern, String detail){
        String method = info.getMethodsCondition().isEmpty() ? ".*" : null;
        if (method == null){
            StringBuilder sb = new StringBuilder("");
            info.getMethodsCondition().getMethods()
                    .forEach(m -> sb.append(m.toString().toLowerCase()).append("|"));
            method = sb.substring(0,sb.length() - 1);
        }
        pattern = pattern.replaceAll("\\{[^\\}]+\\}","*");
        return new Mapping(pattern,method,type,detail);
    }

    @Resource
    private List<MappingManagerConfiguration> configurations;
    @Resource
    private List<MappingInjection> injections;

    /**
     * 获取当前请求匹配的映射
     * 当存在多个匹配的映射时, 获取长度最长的映射
     * @param request 请求
     * @return 匹配的映射
     */
    public Optional<Mapping> getMapping(HttpServletRequest request){
        String url = request.getServletPath();
        String method = request.getMethod().toLowerCase();
        return this.mappings.stream()
                .filter(mapping -> this.matcher.matcher(method, url, mapping))
                .max(Comparator.comparingInt(mapping -> mapping.getUrl().length()));
    }

    public void add(Mapping mapping){
        this.mappings.add(mapping);
        this.mappings = this.mappings.stream()
                .distinct()
                .sorted(Comparator.comparingInt((Mapping m) -> m.getUrl().length()).reversed())
                .collect(Collectors.toList());
    }

}
