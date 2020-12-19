package tech.guyi.web.quick.permission;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import tech.guyi.web.quick.permission.authorization.AuthorizationCurrent;
import tech.guyi.web.quick.permission.configuration.PermissionConfiguration;
import tech.guyi.web.quick.permission.handler.AuthorizationHandler;
import tech.guyi.web.quick.permission.handler.AuthorizationHandlerRepository;
import tech.guyi.web.quick.permission.handler.entry.HandlerRequest;
import tech.guyi.web.quick.permission.mapping.MappingRepository;
import tech.guyi.web.quick.permission.mapping.entry.Mapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * @author guyi
 * 核心拦截器
 */
public class QuickPermissionCoreFilter implements HandlerInterceptor {

    @Resource
    private MappingRepository mappingRepository;
    @Resource
    private AuthorizationHandlerRepository handlerRepository;

    @Resource
    private List<AuthorizationCurrent> currents;
    @Resource
    private PermissionConfiguration configuration;

    private Optional<String> getToken(HttpServletRequest request, String tokenName){
        String token = Optional.ofNullable(request.getParameter(tokenName))
                .orElseGet(() -> request.getHeader(tokenName));
        return Optional.ofNullable(token);
    }

    private void setCurrent(HttpServletRequest request, HttpServletResponse response){
        this.currents.forEach(current ->
                this.getToken(request,current.getTokenName())
                        .filter(current.getMemory()::contains)
                        .ifPresent(token -> {
                            current.currentKey(token);
                            if (configuration.getAuthorization().isAutoRenew()){
                                String key = current.getMemory().renew(token);
                                if (!token.equals(key)){
                                    response.setHeader(configuration.getTokenName(),key);
                                }
                            }
                        })
        );
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<Mapping> mapping = this.mappingRepository.getMapping(request);

        this.setCurrent(request,response);

        if (!mapping.isPresent() || !mapping.get().isAuthorization()){
            return true;
        }

        HandlerRequest handlerRequest = new HandlerRequest(
                request.getServletPath(),
                request.getMethod().toLowerCase(),
                request
        );
        for (AuthorizationHandler handle : this.handlerRepository.getHandles(mapping.get().getAuthorizationType())) {
            HttpStatus status = handle.handle(handlerRequest);
            if (HttpStatus.OK != status){
                if (status != null){
                    response.setStatus(status.value());
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        this.currents.forEach(current -> current.currentKey(null));
    }
}
