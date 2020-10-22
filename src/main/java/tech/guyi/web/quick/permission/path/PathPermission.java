package tech.guyi.web.quick.permission.path;

import lombok.Data;

/**
 * @author guyi
 * 路径权限
 */
@Data
public class PathPermission {

    /**
     * 路径信息
     * /test/test /test/** /test**
     */
    private String path;
    /**
     * 请求方法
     * post get get|post *
     */
    private String method;

}
