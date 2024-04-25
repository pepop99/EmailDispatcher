package com.pepop99.emaildispatcher.handlers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmailHandler extends BaseHandler {
    @Override
    public void process(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        final String uri = httpServletRequest.getRequestURI();
        System.out.println(uri);
        switch (uri) {
            case(""): {

            }
        }
        respond("sd", httpServletResponse, 200);
    }
}
