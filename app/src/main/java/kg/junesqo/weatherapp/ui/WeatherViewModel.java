package kg.junesqo.weatherapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import kg.junesqo.weatherapp.App;
import kg.junesqo.weatherapp.common.Resource;
import kg.junesqo.weatherapp.data.model.WeatherApp;
import kg.junesqo.weatherapp.data.repositories.MainRepository;

public class WeatherViewModel extends ViewModel {

    public LiveData<Resource<WeatherApp>> liveData;

    public WeatherViewModel() {
    }


    public void getWeatherByCityName(String cityName) {
        liveData = App.repository.getWeatherByCityName(cityName);
    }

}
