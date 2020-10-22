package tech.guyi.web.quick.permission.mapping;

import org.springframework.util.AntPathMatcher;
import tech.guyi.web.quick.permission.mapping.entry.Mapping;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.regex.Pattern;

/**
 * @author guyi
 * 请求路径匹配器
 */
public class MappingMatcher {

    /**
     * 路径匹配器队列
     */
    private final Queue<AntPathMatcher> pathMatchers = new LinkedList<>();

    /**
     * 判断请求的url是否同映射匹配
     * @param method 请求的方法
     * @param url 请求诶url
     * @param mapping 映射
     * @return 是否匹配
     */
    public boolean matcher(String method, String url, Mapping mapping){
        AntPathMatcher matcher = Optional.ofNullable(this.pathMatchers.poll())
                .orElseGet(AntPathMatcher::new);
        boolean request = matcher.match(mapping.getUrl(),url);
        this.pathMatchers.add(matcher);
        return request && Pattern.matches(mapping.getMethod(),method);
    }

}
