package com.example.myapplication;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * WeatherResponse class is used to represent the JSON response from the weather API.
 * It contains the weather data for a specific city, including temperature, weather description, and city name.
 */
public class WeatherResponse {

    @SerializedName("name")
    private String cityName;  // Name of the city for which the weather data is retrieved.

    @SerializedName("main")
    private Main main;  // Contains the main weather data like temperature.

    @SerializedName("weather")
    private List<Weather> weatherList;  // A list of weather conditions, such as description of the weather.

    /**
     * Gets the name of the city from the weather data.
     *
     * @return The name of the city.
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Gets the main weather data such as temperature.
     *
     * @return The {@link Main} object containing the main weather data.
     */
    public Main getMain() {
        return main;
    }

    /**
     * Gets the list of weather conditions.
     *
     * @return A list of {@link Weather} objects containing descriptions of the weather conditions.
     */
    public List<Weather> getWeatherList() {
        return weatherList;
    }

    /**
     * Main class contains the primary weather data such as temperature.
     */
    public class Main {

        @SerializedName("temp")
        private double temp;  // The temperature value in the specified units.

        /**
         * Gets the current temperature.
         *
         * @return The current temperature.
         */
        public double getTemp() {
            return temp;
        }
    }

    /**
     * Weather class represents the weather conditions, such as a description of the weather.
     */
    public class Weather {

        @SerializedName("description")
        private String description;  // A brief description of the weather (e.g., "clear sky").

        /**
         * Gets the description of the weather condition.
         *
         * @return The weather description.
         */
        public String getDescription() {
            return description;
        }
    }
}
