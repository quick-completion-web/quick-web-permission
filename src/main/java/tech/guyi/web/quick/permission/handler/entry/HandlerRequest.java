package tech.guyi.web.quick.permission.handler.entry;

import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

@Getter
public class HandlerRequest {

    private final String path;
    private final String method;
    private final HttpServletRequest request;

    public HandlerRequest(String path, String method, HttpServletRequest request) {
        this.path = path;
        this.method = method;
        this.request = request;
    }
}
