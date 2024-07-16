package services;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaAPI {
    public Cambio seleccionDivisas(String divisa1, String divisa2 ){

        String Key = "fcb8b8fdbae1b8e0429380d3";


        URI direcccion = URI.create("https://v6.exchangerate-api.com/v6/"+ Key +"/pair/"+ divisa1 +"/"+ divisa2);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direcccion)
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(),Cambio.class);
        } catch (Exception e) {
            throw new RuntimeException("No encontre esa divisa");
        }


    }



}
