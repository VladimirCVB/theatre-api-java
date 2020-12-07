package fontys.sem3.service;

import fontys.sem3.service.authentication.AuthenticationFilter;
import fontys.sem3.service.authentication.CorsFilter;
import fontys.sem3.service.chatapp.ChatWebSocket;
import fontys.sem3.service.resources.EvenimentResources;
import fontys.sem3.service.resources.UserResources;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketEngine;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.awt.event.KeyEvent;
import java.io.Console;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class deploys CustomApplicationConfig on a Grizzly server
 */
class Publisher {

    private static final URI BASE_URI = URI.create("http://localhost:9090/theater/");

    public static void main(String[] args) {

        try {
            CustomApplicationConfig customApplicationConfig = new CustomApplicationConfig();

            URI baseUri = UriBuilder.fromUri("http://localhost/theater/").port(9090).build();

            // create and start a grizzly server
            HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, customApplicationConfig, false);

            // setup static file handler so that we can also serve html pages.
            StaticHttpHandler staticHandler = new StaticHttpHandler("static");
            staticHandler.setFileCacheEnabled(false);
            server.getServerConfiguration().addHttpHandler(staticHandler,"/static/");

            // Create websocket addon
            WebSocketAddOn webSocketAddOn = new WebSocketAddOn();
            server.getListeners().forEach(listener -> { listener.registerAddOn(webSocketAddOn);});

            // register my websocket app
            ChatWebSocket webSocketApp = new ChatWebSocket();
            WebSocketEngine.getEngine().register("/ws", "/assistant", webSocketApp);

            // Now start the server
            server.start();

            System.out.println("Hosting resources at " + BASE_URI.toURL());

            System.out.println("Try the following GET operations in your internet browser: ");
            String[] getOperations = { BASE_URI.toURL() + "events", BASE_URI.toURL() + "events/0",
                    BASE_URI.toURL() + "events/0/seats"};
            for (String getOperation : getOperations) {
                System.out.println(getOperation);
            }

        } catch (IOException ex) {
            Logger.getLogger(Publisher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
