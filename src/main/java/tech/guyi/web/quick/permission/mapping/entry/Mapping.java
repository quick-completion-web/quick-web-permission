package tech.guyi.web.quick.permission.mapping.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.guyi.web.quick.permission.annotation.Authorization;

import java.util.Objects;

/**
 * @author guyi
 * URL映射实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mapping {

    /**
     * 请求地址
     * 形如 /test/test /test/* /test/**
     */
    private String url;
    /**
     * 请求方法正则匹配
     * 请求方法名称为小写
     */
    private String method;
    /**
     * 路径的授权类型
     * 用于选择不同的授权处理器
     */
    private String authorizationType = Authorization.DEFAULT_TYPE;
    /**
     * 是否需要授权
     */
    private boolean authorization = true;

    public Mapping(String url, String method, String authorizationType) {
        this.url = url;
        this.method = method;
        this.authorizationType = authorizationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mapping mapping = (Mapping) o;
        return url.equals(mapping.url) &&
                method.equals(mapping.method) &&
                authorizationType.equals(mapping.authorizationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method, authorizationType);
    }

}
