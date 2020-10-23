package tech.guyi.web.quick.permission.handler.defaults;

import org.springframework.http.HttpStatus;
import tech.guyi.web.quick.permission.handler.AuthorizationHandler;
import tech.guyi.web.quick.permission.handler.entry.HandlerRequest;

/**
 * @author guyi
 * 默认拦截器
 * 此拦截器不做怎么拦截操作
 * 当没有自定义拦截器时，注入此默认拦截器
 */
public class DefaultAuthorizationHandler implements AuthorizationHandler {

    @Override
    public HttpStatus handle(HandlerRequest request) {
        return HttpStatus.OK;
    }

}
