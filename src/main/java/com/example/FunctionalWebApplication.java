package com.example;

import org.apache.catalina.LifecycleException;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.ipc.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

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
        return route(GET("/hotel/{url}"), getHotelInfo())
                .andRoute(GET("/hello"), getHello());
    }

    private static HandlerFunction getHotelInfo() {
        return request -> {
            String url = request.pathVariable("url");
            ParserBase parser = ParserBase.getParser(url);
            if (parser != null) {
                Hotel hotel = parser.getHotel(url);
                return ok().contentType(APPLICATION_JSON).body(fromObject(hotel));
            } else
                return badRequest().build();
        };
    }

    private static HandlerFunction getHello() {
        return request -> ok().body(fromObject("Hello"));
    }
}
