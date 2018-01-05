package com.example;


public class ParserBooking extends ParserBase {

    public Hotel getHotel(String url) {
        Hotel hotel = new Hotel();
        hotel.setUrl(url);
        hotel.setName("BookingName");
        hotel.setDescription("BookingDescription");
        hotel.setOwner("BookingOwner");
        return hotel;
    }
}
