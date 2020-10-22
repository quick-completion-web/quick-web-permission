package tech.guyi.web.quick.permission.authorization;

import tech.guyi.web.quick.permission.authorization.memory.AuthorizationInfoMemorySupplier;

import javax.annotation.Resource;
import java.util.Optional;

public class AuthorizationCurrent {

    private final ThreadLocal<String> local = new ThreadLocal<>();

    @Resource
    private AuthorizationInfoMemorySupplier supplier;

    public void currentKey(String current) {
        this.local.set(current);
    }

    public Optional<String> currentKey() {
        return java.util.Optional.ofNullable(local.get());
    }

    public Optional<AuthorizationInfo> current(){
        return this.currentKey()
                .flatMap(key -> supplier.getMemory().get(key));
    }

    public <A extends AuthorizationInfo> Optional<A> current(Class<A> classes){
        return this.currentKey()
                .flatMap(key -> supplier.getMemory().get(key,classes));
    }

}
