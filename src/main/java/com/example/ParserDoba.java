package com.example;


public class ParserDoba extends ParserBase {

    public Hotel getHotel(String url) {
        Hotel hotel = new Hotel();
        hotel.setUrl(url);
        hotel.setName("DobaName");
        hotel.setDescription("DobaDescription");
        hotel.setOwner("DobaOwner");
        return hotel;
    }
}
