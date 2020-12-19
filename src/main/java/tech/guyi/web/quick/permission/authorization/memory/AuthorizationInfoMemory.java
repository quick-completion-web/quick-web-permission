package tech.guyi.web.quick.permission.authorization.memory;

import tech.guyi.web.quick.permission.authorization.AuthorizationInfo;

import java.util.Optional;

public interface AuthorizationInfoMemory {

    default String forType(){
        return "map";
    }

    boolean contains(String key);

    <A extends AuthorizationInfo> String save(A authorization, long timespan);

    default <A extends AuthorizationInfo> String save(A authorization){
        return this.save(authorization,System.currentTimeMillis());
    }

    void remove(String key);

    default <A extends AuthorizationInfo> Optional<A> get(String key, Class<A> classes){
        return this.get(key).map(classes::cast);
    }

    Optional<AuthorizationInfo> get(String key);

    String renew(String key);

}
