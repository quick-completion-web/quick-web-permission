package tech.guyi.web.quick.permission.authorization;

import tech.guyi.web.quick.permission.authorization.memory.AuthorizationInfoMemory;
import tech.guyi.web.quick.permission.authorization.memory.AuthorizationInfoMemorySupplier;

import java.util.Optional;

public interface AuthorizationCurrent {

    AuthorizationInfoMemorySupplier getSupplier();

    default AuthorizationInfoMemory getMemory(){
        return this.getSupplier().getMemory();
    }

    String getTokenName();

    void currentKey(String key);

    Optional<String> currentKey();

    default Optional<AuthorizationInfo> current(){
        return this.currentKey()
                .flatMap(key -> this.getSupplier().getMemory().get(key));
    }

    default  <A extends AuthorizationInfo> Optional<A> current(Class<A> classes){
        return this.currentKey()
                .flatMap(key -> this.getSupplier().getMemory().get(key,classes));
    }

}
