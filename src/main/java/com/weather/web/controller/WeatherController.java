package com.weather.web.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

@Controller
public class WeatherController {

    private final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @GetMapping("/")
    public String index(Model model) {
        logger.debug("Welcome to Weather App...");
        model.addAttribute("msg", getMessage());
        model.addAttribute("today", new Date());
        return "index";

    }

    @PostMapping("/weather")
    public String weather(Model model, @RequestParam("city") String city) throws IOException {

        String apikey = "03992f73c58618ae9ab3ee31a6c1ffc3";
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apikey + "";
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream);

        //	Scanner scanner = new Scanner(reader);
        StringBuilder responseContent = new StringBuilder();


        Scanner scanner = new Scanner(reader);

        while(scanner.hasNext())
        {
            responseContent.append(scanner.nextLine());
        }
        scanner.close();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(responseContent.toString(),JsonObject.class);
        System.out.println(jsonObject);


        long dateTimestamp = jsonObject.get("dt").getAsLong() * 1000 ;
        String date = new Date(dateTimestamp).toString();

        double tempratureKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
        int tempratureCelsius = (int)(tempratureKelvin - 273.15);

        int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();

        double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();

        String weatherCondition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();


        model.addAttribute("date", date);
        model.addAttribute("city", city);
        model.addAttribute("temperature", tempratureCelsius);
        model.addAttribute("weatherCondition", weatherCondition);
        model.addAttribute("humidity", humidity);
        model.addAttribute("windSpeed", windSpeed);
        model.addAttribute("weatherData", responseContent.toString());

        connection.disconnect();

        return "index";

    }

    private String getMessage() {
        return "Hello World";
    }

}

