package tech.guyi.web.quick.permission.authorization.memory.defaults;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import tech.guyi.web.quick.permission.authorization.AuthorizationInfo;
import tech.guyi.web.quick.permission.authorization.memory.AuthorizationInfoMemory;
import tech.guyi.web.quick.permission.authorization.configuration.AuthorizationConfiguration;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Data
class AuthorizationInfoEntry {

    private String token;
    private AuthorizationInfo authorization;
    private long timespan;

    public AuthorizationInfoEntry(String token,AuthorizationInfo authorization){
        this.token = token;
        this.authorization = authorization;
        this.timespan = System.currentTimeMillis();
    }

}

public class MapAuthorizationInfoMemory implements AuthorizationInfoMemory, InitializingBean {

    private final Map<String,AuthorizationInfoEntry> authorizations = new HashMap<>();

    @Override
    public boolean contains(String key) {
        return this.authorizations.containsKey(key);
    }

    @Override
    public <A extends AuthorizationInfo> String save(A authorization, long timespan) {
        String key = UUID.randomUUID().toString().replaceAll("-","");
        this.authorizations.put(key,new AuthorizationInfoEntry(key,authorization));
        return key;
    }

    @Override
    public void remove(String key) {
        this.authorizations.remove(key);
    }

    @Override
    public Optional<AuthorizationInfo> get(String key) {
        return Optional.ofNullable(this.authorizations.get(key))
                .map(AuthorizationInfoEntry::getAuthorization);
    }

    @Override
    public String renew(String key) {
        if (this.contains(key)){
            AuthorizationInfoEntry entry = this.authorizations.get(key);
            entry.setTimespan(System.currentTimeMillis());
        }
        return key;
    }

    @Resource
    private AuthorizationConfiguration configuration;
    private final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    @Override
    public void afterPropertiesSet() {
        if (configuration.getType().equals(this.forType()) && configuration.getTimeout() != -1){
            service.scheduleWithFixedDelay(() -> {
                List<AuthorizationInfoEntry> values = new LinkedList<>(authorizations.values());
                long now = System.currentTimeMillis();
                values.stream()
                        .filter(entry -> (now - entry.getTimespan()) >= configuration.getTimeout())
                        .map(AuthorizationInfoEntry::getToken)
                        .forEach(authorizations::remove);
            }, 0, ((int)(configuration.getTimeout() * 0.8)), TimeUnit.MILLISECONDS);
        }
    }

}
