package org.nordmann.java9;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by yoavn on 5/16/17.
 */
public class Http2Example {

    public static void main(String[] args) {


    }


    public static void exampleOld1() throws IOException {
        // HTTP GET request


        String url = "http://www.google.com/search?q=mkyong";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Mozilla");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());


    }


    public static void example2() throws IOException, InterruptedException, URISyntaxException {

        HttpRequest request = HttpRequest.newBuilder(new URI("http://www.foo.com"))
                .headers("Foo", "foovalue", "Bar", "barvalue")
                .GET().build();

        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandler.asString());

        HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandler.asString())
                .thenAccept(stringHttpResponse -> {
                    System.out.println("Received Response");
                });

    }

}
