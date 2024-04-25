package com.pepop99.emaildispatcher.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MetadataHandler extends BaseHandler {
    @Override
    public void process(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        final String uri = httpServletRequest.getRequestURI();
        switch (uri) {
            case (APIHandlerConstants.URI_SAVE_NON_PROFITS) -> {
                System.out.println("np");
                respond("Saved non profit meta", httpServletResponse, 200);
            }
            case (APIHandlerConstants.URI_SAVE_FOUNDATIONS) -> {
                System.out.println("fd");
                respond("Saved foundation meta", httpServletResponse, 200);
            }
            default -> respond("Invalid request", httpServletResponse, 404);
        }
    }
}
