package tech.guyi.web.quick.permission.handler.defaults;

import org.springframework.http.HttpStatus;
import tech.guyi.web.quick.permission.authorization.AuthorizationCurrent;
import tech.guyi.web.quick.permission.handler.AuthorizationHandler;
import tech.guyi.web.quick.permission.handler.entry.HandlerRequest;

import javax.annotation.Resource;

/**
 * @author guyi
 * Token拦截器
 * 当没有携带有效token时请求被拦截
 */
public class TokenAuthorizationHandler implements AuthorizationHandler {

    @Resource
    private AuthorizationCurrent current;

    @Override
    public HttpStatus handle(HandlerRequest request) {
        return current.currentKey()
                .map(key -> HttpStatus.OK)
                .orElse(HttpStatus.UNAUTHORIZED);
    }

}
