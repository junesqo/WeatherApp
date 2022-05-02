package kg.junesqo.weatherapp.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import kg.junesqo.weatherapp.data.model.WeatherApp;

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherApp weather);

    @Query("SELECT * FROM weatherapp")
    WeatherApp getWeather();
}