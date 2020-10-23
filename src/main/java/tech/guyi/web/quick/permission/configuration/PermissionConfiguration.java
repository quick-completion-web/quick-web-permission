package tech.guyi.web.quick.permission.configuration;

import lombok.Data;

@Data
public class PermissionConfiguration {

    /**
     * 登录Token的键名称
     */
    private String tokenName = "Authorization";
    private boolean enableDefaultTokenHandler = true;
    private AuthorizationConfiguration authorization = new AuthorizationConfiguration();

}
