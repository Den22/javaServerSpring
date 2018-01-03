package com.example;

import lombok.Data;
import org.apache.catalina.LifecycleException;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.ipc.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class FunctionalWebApplication {

    public static void main(String[] args) throws IOException, LifecycleException, InterruptedException {

        RouterFunction router = getRouter();

        HttpHandler httpHandler = RouterFunctions.toHttpHandler(router);

        HttpServer
                .create("localhost", 8082)
                .newHandler(new ReactorHttpHandlerAdapter(httpHandler))
                .block();

        Thread.currentThread().join();
    }

    static RouterFunction getRouter() {
        HandlerFunction hello = request -> ok().body(fromObject("Hello"));
        HandlerFunction helloWorld = req -> ok().contentType(APPLICATION_JSON).body(fromObject(new Hello("world")));

        return route(GET("/"), hello)
                .andRoute(GET("/json"), helloWorld);
    }
}
