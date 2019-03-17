package com.app.harish.wordie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

class GetJSONClass {

    //https://api.wordnik.com/v4/word.json/Happy/definitions?limit=20&includeRelated=false&useCanonical=true&includeTags=true&api_key=YOURAPIKEY

    static String getJSON(String word){

        String URLfirstpart = "https://api.wordnik.com/v4/word.json/";
        String URLlastpart = "/definitions?limit=20&includeRelated=false&useCanonical=true&includeTags=true&api_key=";
        /*NOTE: The app wont function until you get your api key from wordnik(https://developer.wordnik.com/)
         *and store in API String
         */
        String API ="PasteHere";

        String link = URLfirstpart+word+URLlastpart+API;
        String JSONString;
        URL youarel;

        HttpURLConnection Hcon = null;
        InputStream ipstream = null;
        StringBuilder sb = new StringBuilder();
        try {
            youarel = new URL(link);//malformed
            Hcon = (HttpURLConnection) youarel.openConnection();//IOException
            Hcon.setRequestMethod("GET");
            Hcon.connect();

            if (Hcon.getResponseCode() == 200) {
                ipstream = Hcon.getInputStream();
                if (ipstream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(ipstream, Charset.forName("UTF-8"));
                    BufferedReader bipstream = new BufferedReader(inputStreamReader);
                    String Lines = bipstream.readLine();
                    while (Lines != null) {
                        //add chars line by line into string builder!
                        sb.append(Lines);
                        Lines = bipstream.readLine();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Hcon != null) {
                Hcon.disconnect();
            }
            if (ipstream != null) {
                // function must handle java.io.IOException here
                try {
                    ipstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            JSONString = sb.toString();

        }
        return  JSONString;

    }
}


