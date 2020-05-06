package com.rined.gossip.interceptors;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class RedirectInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {
        if (Objects.nonNull(modelAndView)) {
            String args = Objects.nonNull(request.getQueryString()) ? request.getQueryString() : "";
            response.setHeader("Turbolinks-Location",
                    args.length() == 0
                            ? request.getRequestURI()
                            : String.format("%s?%s", request.getRequestURI(), args)
            );
        }
    }
}
