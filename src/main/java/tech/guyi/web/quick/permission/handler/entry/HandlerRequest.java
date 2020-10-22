package tech.guyi.web.quick.permission.handler.entry;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

@Getter
@AllArgsConstructor
public class HandlerRequest {

    private final String path;
    private final String method;
    private final HttpServletRequest request;

}
