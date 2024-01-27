package com.example.smartc;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Binance {
    public static void main(String[] args) {
        String url = "https://api.binance.com/api/v3/ticker/price";
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse res = client.execute(httpGet);
            HttpEntity entity = res.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");

            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(result.toString(), JsonArray.class);
            for (JsonElement element : jsonArray) {
                JsonObject json = element.getAsJsonObject();
                String symbol = json.get("symbol").getAsString();
                String price = json.get("price").getAsString();
                System.out.println("Цена " + symbol + ": " + price);
            }
        }catch (Exception e) {

        }

    }
}
