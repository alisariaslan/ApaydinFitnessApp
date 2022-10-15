package com.pakachu.apaydinfitness;

public class Credentials {
    private static String url_select = "http://bahadirduzcan.com.tr/pakachu/quick/select/";
    private static String url_other = "http://bahadirduzcan.com.tr/pakachu/quick/";
    private static String token = "Pakachu-Token";
    private static String user_agent = "CenutaUA22";

    //TEST
    private static String add_id = "ca-app-pub-8946455145480386/2153579296";

    //REAL
//    private static String add_id="ca-app-pub-3940256099942544/1033173712";

    public static String getUrl_select() {
        return url_select;
    }

    public static String getUrl_other() {
        return url_other;
    }

    public static String getToken() {
        return token;
    }

    public static String getUser_agent() {
        return user_agent;
    }

    public static String getAdd_id() {
        return add_id;
    }

}
