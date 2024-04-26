package com.pepop99.emaildispatcher.handlers;

import com.pepop99.emaildispatcher.exceptions.DuplicateEmailException;
import com.pepop99.emaildispatcher.metadata.AppMeta;
import com.pepop99.emaildispatcher.metadata.FoundationMeta;
import com.pepop99.emaildispatcher.metadata.NonProfitMeta;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class MetadataHandler extends BaseHandler {
    @Override
    public void process(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        final String uri = httpServletRequest.getRequestURI();
        // not adding any validations assuming data passed to api is already validated
        final String email = httpServletRequest.getParameter(APIHandlerConstants.EMAIL);
        final String name = httpServletRequest.getParameter(APIHandlerConstants.NAME);
        final String address = httpServletRequest.getParameter(APIHandlerConstants.ADDRESS);
        switch (uri) {
            case (APIHandlerConstants.URI_SAVE_NON_PROFITS) -> {
                try {
                    AppMeta.instance.insertNonProfitMeta(email, name, address);
                } catch (DuplicateEmailException e) {
                    respond(e.getMessage(), httpServletResponse, 500);
                    return;
                }
                respond("Saved non profit meta", httpServletResponse, 200);
            }
            case (APIHandlerConstants.URI_SAVE_FOUNDATIONS) -> {
                try {
                    AppMeta.instance.insertFoundationMeta(email);
                } catch (DuplicateEmailException e) {
                    respond(e.getMessage(), httpServletResponse, 500);
                    return;
                }
                respond("Saved foundation meta", httpServletResponse, 200);
            }
            case (APIHandlerConstants.URI_LIST_ALL_NON_PROFITS) -> {
                final ArrayList<NonProfitMeta> list = new ArrayList<>();
                AppMeta.instance.getAllNonProfits(list);
                respond(list.toString(), httpServletResponse, 200);
            }
            case (APIHandlerConstants.URI_LIST_ALL_FOUNDATIONS) -> {
                final ArrayList<FoundationMeta> list = new ArrayList<>();
                AppMeta.instance.getAllFoundations(list);
                respond(list.toString(), httpServletResponse, 200);
            }
            default -> respond("Invalid request", httpServletResponse, 404);
        }
    }
}
