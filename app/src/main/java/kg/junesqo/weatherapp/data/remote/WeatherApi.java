package kg.junesqo.weatherapp.data.remote;

import kg.junesqo.weatherapp.data.model.WeatherApp;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("data/2.5/weather")
    Call<WeatherApp> getTemp(
            @Query("lat") String lat,
            @Query("lon") String lon,
//            @Query("q") String city,
            @Query("appid") String apiKey
            );
}
