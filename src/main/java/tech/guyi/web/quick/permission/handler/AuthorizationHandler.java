package tech.guyi.web.quick.permission.handler;

import org.springframework.http.HttpStatus;
import tech.guyi.web.quick.permission.annotation.Authorization;
import tech.guyi.web.quick.permission.handler.entry.HandlerRequest;

/**
 * @author guyi
 * 授权处理器
 */
public interface AuthorizationHandler {

    /**
     * 支持的授权类型
     * @return 授权类型
     */
    default String forType(){
        return Authorization.DEFAULT_TYPE;
    }

    /**
     * 排序
     * 值越大, 执行越靠后
     * @return 排序值
     */
    default int order(){
        return 999;
    }

    /**
     * 授权处理
     * @param request 请求
     * @return 响应状态
     */
    HttpStatus handle(HandlerRequest request) throws Exception;

}
