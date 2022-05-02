package kg.junesqo.weatherapp.data.local.converters;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import kg.junesqo.weatherapp.data.model.Sys;

public class SysConverter {

    @TypeConverter
    public String fromSysToString(Sys main){
        if (main == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Sys>(){}.getType();
        return gson.toJson(main,type);
    }
    @TypeConverter
    public Sys fromStringToSys(String fromString){
        if (fromString == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Sys>(){}.getType();
        return gson.fromJson(fromString,type);
    }
}
