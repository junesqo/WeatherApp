package kg.junesqo.weatherapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kg.junesqo.weatherapp.common.Resource;
import kg.junesqo.weatherapp.data.local.WeatherDao;
import kg.junesqo.weatherapp.data.model.WeatherApp;
import kg.junesqo.weatherapp.data.repositories.MainRepositoryImpl;

@HiltViewModel
public class WeatherViewModel extends ViewModel {




    public LiveData<Resource<WeatherApp>> liveData;
    private MainRepositoryImpl repository;

    public WeatherViewModel() {
    }

    @Inject
    public WeatherViewModel(MainRepositoryImpl repository) {
        this.repository = repository;
    }

    @Inject
    WeatherDao dao;

//    public void getWeatherByCityName(String cityName) {
//        liveData = repository.getWeatherByCityName(cityName);
//    }

    public void getWeatherByMap(String latitude, String longitude) {
        liveData = repository.getWeatherByMap(latitude, longitude);
    }
}
