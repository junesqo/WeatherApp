package kg.junesqo.weatherapp.data.remote;

import kg.junesqo.weatherapp.R;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();

    public WeatherApi provideApi() {
        return retrofit.create(WeatherApi.class);
    }

}
