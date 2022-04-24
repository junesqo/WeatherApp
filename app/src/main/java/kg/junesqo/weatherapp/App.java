package kg.junesqo.weatherapp;

import android.app.Application;

import kg.junesqo.weatherapp.data.remote.RetrofitClient;
import kg.junesqo.weatherapp.data.repositories.MainRepository;

public class App extends Application {

    private RetrofitClient retrofitClient;
    public static MainRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofitClient = new RetrofitClient();
        repository = new MainRepository(retrofitClient.provideApi());
    }
}
