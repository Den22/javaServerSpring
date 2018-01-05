package com.example;

import org.apache.tomcat.util.buf.Utf8Encoder;
import org.junit.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class FunctionalWebApplicationTests {

    private final WebTestClient webTestClient =
            WebTestClient.bindToRouterFunction(FunctionalWebApplication.getRouter()).build();

    @Test
    public void testHello() {
        webTestClient.get().uri("/hello").exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("Hello");
    }

    @Test
    public void testInfoHotelDoba() throws UnsupportedEncodingException {
        String urlParam = "doba.ua/vinnitsa/163950.html";
        String urlParamEncoder = URLEncoder.encode(urlParam, "UTF-8");
        Hotel hotel = new Hotel();
        hotel.setUrl(urlParamEncoder);
        hotel.setName("DobaName");
        hotel.setDescription("DobaDescription");
        hotel.setOwner("DobaOwner");
        webTestClient.get().uri("/hotel/" + urlParamEncoder).exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(Hotel.class)
                .isEqualTo(hotel);
    }

    @Test
    public void testInfoHotelBooking() throws UnsupportedEncodingException {
        String urlParam = "booking.com/hotel/ua/ramada-lviv.ru.html";
        String urlParamEncoder = URLEncoder.encode(urlParam, "UTF-8");
        Hotel hotel = new Hotel();
        hotel.setUrl(urlParamEncoder);
        hotel.setName("BookingName");
        hotel.setDescription("BookingDescription");
        hotel.setOwner("BookingOwner");
        webTestClient.get().uri("/hotel/" + urlParamEncoder).exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(Hotel.class)
                .isEqualTo(hotel);
    }

    @Test
    public void testInfoHotelBadRequest() {
        webTestClient.get().uri("/hotel/abracadabra").exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testInfoHotel404() {
        webTestClient.get().uri("/404").exchange()
                .expectStatus().is4xxClientError();
    }

}
