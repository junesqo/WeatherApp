package kg.junesqo.weatherapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import kg.junesqo.weatherapp.R;
import kg.junesqo.weatherapp.base.BaseFragment;
import kg.junesqo.weatherapp.common.Resource;
import kg.junesqo.weatherapp.data.local.WeatherDao;
import kg.junesqo.weatherapp.data.model.Clouds;
import kg.junesqo.weatherapp.data.model.Coord;
import kg.junesqo.weatherapp.data.model.Main;
import kg.junesqo.weatherapp.data.model.Sys;
import kg.junesqo.weatherapp.data.model.Weather;
import kg.junesqo.weatherapp.data.model.WeatherApp;
import kg.junesqo.weatherapp.data.model.Wind;
import kg.junesqo.weatherapp.databinding.FragmentWeatherBinding;

@AndroidEntryPoint
public class WeatherFragment extends BaseFragment<FragmentWeatherBinding> {

    private WeatherFragmentArgs args;

    private Main main;
    private Sys sys;
    private ArrayList<Weather> weatherList = new ArrayList<>();
    private WeatherApp weather;
    private Wind wind;

    private String cityName;

    private WeatherViewModel weatherViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
//        cityName = "Bishkek";
        try {
            args = WeatherFragmentArgs.fromBundle(getArguments());
        } catch (Exception e) {
            Log.e("Error:", e.getLocalizedMessage());
        }
//        weatherViewModel.getWeatherByCityName(args.getCityName());
        weatherViewModel.getWeatherByMap(args.getLatitude(), args.getLongitude());
    }

    @Override
    protected FragmentWeatherBinding bind() {
        return FragmentWeatherBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {

    }

    @Override
    protected void callRequests() {
//        Log.e("args", args.getCityName());

//        if (args != null) {
////            args = WeatherFragmentArgs.fromBundle(getArguments());
//            Log.e("args", args.getCityName());
//            cityName = args.getCityName();
//        } else {
//            Log.e("args", "args is empty");
//            cityName = "Bishkek";
//        }
//        weatherViewModel.getWeatherByCityName(cityName);
//        weatherViewModel.getWeatherByMap(args.getLatitude(), args.getLongitude());
    }

    @Override
    protected void setupListeners() {
        binding.rectangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_weatherFragment_to_weatherSearchFragment);
            }
        });

    }

    @Override
    protected void setupObservers() {
        weatherViewModel.liveData.observe(getViewLifecycleOwner(), new Observer<Resource<WeatherApp>>() {
            @Override
            public void onChanged(Resource<WeatherApp> resource) {
                switch (resource.status) {
                    case LOADING: {
                        binding.cardView.setVisibility(View.GONE);
                        binding.progressBar.setVisibility(View.VISIBLE);
                        binding.imageIv.setVisibility(View.GONE);
                        break;
                    }
                    case SUCCESS: {
                        binding.cardView.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.GONE);
                        binding.imageIv.setVisibility(View.VISIBLE);

                        wind = resource.data.getWind();
                        weather = resource.data;
                        main = resource.data.getMain();
                        sys = resource.data.getSys();
                        weatherList = (ArrayList<Weather>) resource.data.getWeather();
                        binding.progressBar.setVisibility(View.GONE);
                        setCurrentWeather();
                        break;
                    }
                    case ERROR: {
                        Toast.makeText(requireContext(), resource.msg, Toast.LENGTH_SHORT).show();
                        wind = weatherViewModel.dao.getWeather().getWind();
                        weather = weatherViewModel.dao.getWeather();
                        main = weatherViewModel.dao.getWeather().getMain();
                        sys = weatherViewModel.dao.getWeather().getSys();
                        weatherList = (ArrayList<Weather>) weatherViewModel.dao.getWeather().getWeather();
                        binding.progressBar.setVisibility(View.GONE);
                        setCurrentWeather();
                        binding.cardView.setVisibility(View.VISIBLE);
                        binding.imageIv.setVisibility(View.VISIBLE);

                        break;
                    }
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentWeather() {

        binding.locationTv.setText(cityName);
        binding.dateTv.setText(getDate(System.currentTimeMillis()));

        //Setting weather status
        binding.weatherStatus.setText(weatherList.get(0).getMain());
        Glide.with(requireContext())
                .load("https://openweathermap.org/img/wn/" + weatherList.get(0).getIcon() + ".png")
                .override(100, 100)
                .into(binding.weatherStatusImg);

        //Setting temperature
        binding.tempTv.setText(Math.round(main.getTemp() - 273.15) + "");
        binding.minTempTv.setText(Math.round(main.getTempMin() - 273.15) + "??C");
        binding.maxTempTv.setText(Math.round(main.getTempMax() - 273.15) + "??C");

        //Setting extras
        binding.humidityTv.setText(main.getHumidity() + "%");
        binding.pressureTv.setText(main.getPressure() + "mBar");
        binding.windTv.setText(wind.getSpeed() + "km/h");

        //Setting sunset and sunrise
        binding.sunriseTv.setText(getTime(requireContext(), Long.valueOf(sys.getSunrise())));
        binding.sunsetTv.setText(getTime(requireContext(), Long.valueOf(sys.getSunset())));

        //Setting daytime
        int daytime = sys.getSunset() - sys.getSunrise();
        binding.daytimeTv.setText(getHours(daytime));

        //Set city
        cityName = sys.getCountry();
//        cityName = weather.getName();
    }

    @SuppressLint("SimpleDateFormat")
    private String getDate(Long date) {
        return new SimpleDateFormat("EEE, d MMMM yyyy | h:mm a").format(date);
    }

    private String getTime(Context context, Long time) {
        return DateUtils.formatDateTime(context, time * 1000, DateUtils.FORMAT_SHOW_TIME);
    }

    private String getHours(int time) {

        int hours = (int) TimeUnit.SECONDS.toHours(time);
        int minutes = (int) ((int) TimeUnit.SECONDS.toMinutes(time) -
                (TimeUnit.SECONDS.toHours(time) * 60));

        return hours + "h " + minutes + "m";
    }
}