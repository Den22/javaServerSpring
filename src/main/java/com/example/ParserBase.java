package com.example;


import org.springframework.util.StringUtils;

public abstract class ParserBase {

    public abstract Hotel getHotel(String url);

    static ParserBase getParser(String url) {
        String site = url.replaceFirst("\\..*$", "");
        try {
            Class cl = Class.forName("com.example.Parser" + StringUtils.capitalize(site));
            return (ParserBase) cl.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
        return null;
    }
}
