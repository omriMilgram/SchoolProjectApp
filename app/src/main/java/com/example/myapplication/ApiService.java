package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * ApiService interface defines the method for fetching weather data from the OpenWeather API.
 * It utilizes Retrofit for making network requests.
 */
public interface ApiService {

    /**
     * Fetches weather data for a specific city.
     *
     * @param cityName The name of the city to fetch weather data for.
     * @param units The units for the temperature measurement (e.g., "metric" for Celsius).
     * @param apiKey The API key required to authenticate the request to the weather service.
     *
     * @return A {@link Call} object that can be used to send the request and receive the response
     *         asynchronously, with a response expected as a {@link WeatherResponse} object.
     */
    @GET("data/2.5/weather")
    Call<WeatherResponse> getWeatherData(
            @Query("q") String cityName,
            @Query("units") String units,
            @Query("appid") String apiKey
    );
}
