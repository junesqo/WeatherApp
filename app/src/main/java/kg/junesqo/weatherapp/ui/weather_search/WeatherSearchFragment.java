package kg.junesqo.weatherapp.ui.weather_search;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import kg.junesqo.weatherapp.base.BaseFragment;
import kg.junesqo.weatherapp.databinding.FragmentWeatherSearchBinding;

public class WeatherSearchFragment extends BaseFragment<FragmentWeatherSearchBinding> {

    @Override
    protected FragmentWeatherSearchBinding bind() {
        return FragmentWeatherSearchBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupViews() {

    }

    @Override
    protected void callRequests() {

    }

    @Override
    protected void setupListeners() {
        binding.searchCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.findCityET.getText() != null) {
                    String cityName = binding.findCityET.getText().toString();
                    Log.e("Find city", String.valueOf(binding.findCityET.getText()));
                    navController.navigate(WeatherSearchFragmentDirections
                            .actionWeatherSearchFragmentToWeatherFragment(cityName));
                } else {
                    Toast.makeText(requireContext(), "Write city name!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void setupObservers() {

    }
}
