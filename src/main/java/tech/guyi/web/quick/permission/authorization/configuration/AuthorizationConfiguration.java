package tech.guyi.web.quick.permission.authorization.configuration;

import lombok.Data;

@Data
public class AuthorizationConfiguration {

    private String type = "map";
    /**
     * 是否自动延期
     */
    private boolean autoRenew = true;
    /**
     * 登录超时时间
     * 单位毫秒
     * -1表示永不超时
     */
    private long timeout = 30 * 60 * 1000;

}
