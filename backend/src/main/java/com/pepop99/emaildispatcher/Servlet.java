package com.pepop99.emaildispatcher;

import com.pepop99.emaildispatcher.handlers.BaseHandler;
import com.pepop99.emaildispatcher.handlers.EmailHandler;
import com.pepop99.emaildispatcher.handlers.MetadataHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.util.ArrayList;

public class Servlet {
    private static final int MAX_QTP_THREADS = 10;
    private static final int PORT = 8080;
    private static volatile Server server;

    public static void main(String[] args) throws Exception {
        initialiseJettyServer();
        System.out.println("Hello world!");
    }

    private static void initialiseJettyServer() throws Exception {
        QueuedThreadPool qtp = new QueuedThreadPool(MAX_QTP_THREADS);
        server = new Server(qtp);

        ArrayList<Handler> handlers = new ArrayList<>();
        handlers.add(createContextHandler(new MetadataHandler(), "/meta"));
        handlers.add(createContextHandler(new EmailHandler(), "/email"));
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(handlers.toArray(Handler[]::new));
        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSendDateHeader(false);
        httpConfig.setSendServerVersion(false);
        try (ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(httpConfig))) {
            http.setPort(PORT);
            server.setConnectors(new Connector[]{http});
        }
        server.setHandler(contexts);
        server.start();
    }

    private static ContextHandler createContextHandler(BaseHandler handler, String contextPath) {
        final ContextHandler contextHandler = new ContextHandler();
        contextHandler.setHandler(handler);
        contextHandler.setContextPath(contextPath);
        return contextHandler;
    }
}