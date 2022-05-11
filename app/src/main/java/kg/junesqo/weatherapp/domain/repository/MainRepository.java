package kg.junesqo.weatherapp.domain.repository;

import androidx.lifecycle.MutableLiveData;

import kg.junesqo.weatherapp.common.Resource;
import kg.junesqo.weatherapp.data.model.WeatherApp;

public interface MainRepository {

//    MutableLiveData<Resource<WeatherApp>> getWeatherByCityName(String cityName);

    MutableLiveData<Resource<WeatherApp>> getWeatherByMap(String latitude, String longitude);
}
