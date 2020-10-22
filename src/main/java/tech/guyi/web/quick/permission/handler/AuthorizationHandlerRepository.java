package tech.guyi.web.quick.permission.handler;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author guyi
 * 授权处理器仓库
 */
public class AuthorizationHandlerRepository implements InitializingBean {

    private final Map<String, List<AuthorizationHandler>> handlers = new HashMap<>();

    @Resource
    private List<AuthorizationHandler> handlerList;
    @Override
    public void afterPropertiesSet() {
        this.handlerList.forEach(handler -> {
            List<AuthorizationHandler> list = this.getHandles(handler.forType());
            list.add(handler);
            this.handlers.put(handler.forType(),list);
        });
        this.handlers.values().forEach(list -> list.sort(Comparator.comparingInt(AuthorizationHandler::order)));
    }

    /**
     * 根据类型获取授权处理器
     * @param type 类型
     * @return 授权处理器集合
     */
    public List<AuthorizationHandler> getHandles(String type){
        return Optional.ofNullable(this.handlers.get(type))
                .orElseGet(LinkedList::new);
    }

    /**
     * 添加授权处理器
     * @param handler 授权处理器
     */
    public void add(AuthorizationHandler handler){
        List<AuthorizationHandler> list = this.getHandles(handler.forType());
        list.add(handler);
        list.sort(Comparator.comparingInt(AuthorizationHandler::order));
        this.handlers.put(handler.forType(),list);
    }

}
