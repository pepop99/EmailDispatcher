package com.pepop99.emaildispatcher.handlers;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class BaseHandler extends AbstractHandler {
    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        process(s, httpServletRequest, httpServletResponse);
    }

    public abstract void process(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException;

    public static void respond(String out, HttpServletResponse response, int httpStatus) throws IOException {
        if (out != null) {
            response.setStatus(httpStatus);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            byte[] bytes = out.getBytes(StandardCharsets.UTF_8);
            response.setContentLength(bytes.length);
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }
}
