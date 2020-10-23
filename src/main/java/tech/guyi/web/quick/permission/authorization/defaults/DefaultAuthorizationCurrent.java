package tech.guyi.web.quick.permission.authorization.defaults;

import tech.guyi.web.quick.permission.authorization.AuthorizationCurrent;
import tech.guyi.web.quick.permission.authorization.memory.AuthorizationInfoMemorySupplier;
import tech.guyi.web.quick.permission.configuration.PermissionConfiguration;

import javax.annotation.Resource;
import java.util.Optional;

public class DefaultAuthorizationCurrent implements AuthorizationCurrent {

    private final ThreadLocal<String> local = new ThreadLocal<>();

    @Resource
    private PermissionConfiguration configuration;
    @Resource
    private AuthorizationInfoMemorySupplier supplier;

    @Override
    public AuthorizationInfoMemorySupplier getSupplier() {
        return this.supplier;
    }

    @Override
    public String getTokenName() {
        return configuration.getTokenName();
    }

    public void currentKey(String current) {
        this.local.set(current);
    }

    public Optional<String> currentKey() {
        return java.util.Optional.ofNullable(local.get());
    }

}

