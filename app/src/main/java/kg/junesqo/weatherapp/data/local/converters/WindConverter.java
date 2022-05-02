package kg.junesqo.weatherapp.data.local.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import kg.junesqo.weatherapp.data.model.Wind;

public class WindConverter {
    @TypeConverter
    public String fromWindToString(Wind main){
        if (main == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Wind>(){}.getType();
        return gson.toJson(main,type);
    }
    @TypeConverter
    public Wind fromStringToWind(String fromString){
        if (fromString == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Wind>(){}.getType();
        return gson.fromJson(fromString,type);
    }
}
