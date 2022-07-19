package com.example.rsep4.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rsep4.R;
import com.example.rsep4.adapter.WeatherAdapter;
import com.example.rsep4.models.WeatherModel;
import com.example.rsep4.viewmodels.WeatherDetailsViewModel;
import com.example.rsep4.viewmodels.WeatherViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Date;
import java.time.LocalDateTime;

public class WeatherModifyFragment extends Fragment {

    private WeatherModel weatherObject;
    private WeatherDetailsViewModel mViewModel;
    private ImageView picture;
    private EditText location;
    private EditText avgTemp;
    private EditText minTemp;
    private EditText maxTemp;
    private EditText description;
    private EditText pressure;
    private EditText wind;
    private EditText humidity;
    private EditText sunsetTime;
    private EditText sunriseTime;
    private EditText status;
    private TextView errorText;
    private ProgressBar loader;
    FloatingActionButton fabEdit;
    FloatingActionButton fabBack;

    public static WeatherModifyFragment newInstance() {
        return new WeatherModifyFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        // Setting all layouts
        loader = view.findViewById(R.id.modifyLoader);
        errorText = view.findViewById(R.id.modifyErrorText);
        picture= view.findViewById(R.id.modifyPicture);
        location= view.findViewById(R.id.modifyLocation);
        avgTemp= view.findViewById(R.id.modifyAvgTemp);
        minTemp= view.findViewById(R.id.modifyMinTemp);
        maxTemp= view.findViewById(R.id.modifyMaxTemp);
        description= view.findViewById(R.id.modifyDescription);
        pressure= view.findViewById(R.id.modifyPressure);
        wind= view.findViewById(R.id.modifyWind);
        humidity= view.findViewById(R.id.modifyHumidity);
        sunsetTime= view.findViewById(R.id.modifySunsetTime);
        sunriseTime= view.findViewById(R.id.modifySunriseTime);
        status= view.findViewById(R.id.modifyStatus);
        fabEdit = view.findViewById(R.id.fabEdit);
        fabBack = view.findViewById(R.id.fabBack);

        // Logic for WeatherModifyFragment
        loader.setVisibility(View.VISIBLE);
        mViewModel = new ViewModelProvider(getActivity()).get(WeatherDetailsViewModel.class);

        try {
            if(mViewModel.getWeatherObjectObserver()!=null) {
                mViewModel.getWeatherDetails(mViewModel.getCity());
                mViewModel.getWeatherObjectObserver().observe(getViewLifecycleOwner(), weatherModels -> {
                    if (weatherModels != null) {
                        loader.setVisibility(View.GONE);
                        weatherObject = weatherModels;
                        updateValues(weatherObject);
                    } else {
                        loader.setVisibility(View.GONE);
                    }
                });
            }
            else {
                loader.setVisibility(View.GONE);
            }
        }
        catch (Exception e) {
            Log.e("error in gettin details", e.getMessage() );
        }

        // FAB onclick -> should go to edit existing location fragment
        fabEdit.setOnClickListener( view1 ->
        {
            try{
                String[] locationStrings = location.getText().toString().split(",");
                String city = locationStrings[0];
                String country = locationStrings[1];
                int avgTempToAdd = Integer.parseInt(avgTemp.getText().toString());
                int minTempToAdd = Integer.parseInt(minTemp.getText().toString());
                int maxTempToAdd = Integer.parseInt(maxTemp.getText().toString());
                int humidityToAdd = Integer.parseInt(humidity.getText().toString());
                int pressureToAdd = Integer.parseInt(pressure.getText().toString());
                int windToAdd = Integer.parseInt(wind.getText().toString());
                String sunsetTimeToAdd = sunsetTime.getText().toString();
                String descriptionToAdd = description.getText().toString();
                String sunriseTimeToAdd = sunriseTime.getText().toString();
                String statusToAdd = status.getText().toString();
                String updatedAtToAdd = LocalDateTime.now().toString();
                String pictureToAdd = "";
                WeatherModel weatherModel = new WeatherModel(city, country,
                        avgTempToAdd, minTempToAdd, maxTempToAdd, humidityToAdd,
                        pressureToAdd, windToAdd, descriptionToAdd, pictureToAdd,
                        updatedAtToAdd,statusToAdd,sunriseTimeToAdd, sunsetTimeToAdd);
                if (mViewModel.getWeatherObjectObserver().getValue() != null) {
                    weatherModel.setPicture(mViewModel.getWeatherObjectObserver().getValue().getPicture());
                    mViewModel.updateWeatherDetails(mViewModel.getCity(), weatherModel);
                }
                else {
                    mViewModel.createWeather(weatherModel);
                }
            }
            catch (Exception e)
            {
                Log.e("error adding weather", e.getMessage());
            }
            if (mViewModel.getWeatherObjectObserver().getValue() != null) {

                mViewModel.updateWeatherDetails(mViewModel.getCity(), mViewModel.getWeatherObjectObserver().getValue());
            }
        });

        fabBack.setOnClickListener(view1 -> {
            if(this.getActivity() != null) {
                this.getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(this.getId(), WeatherFragment.newInstance())
                        .commitNow();
            }
            else
            {
                Snackbar.make(view, "Unable to go back", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });

        return view;
    }
    public void updateValues(WeatherModel weatherObject)
    {
        location.setText(String.format("%s, %s", weatherObject.getCity(), weatherObject.getCountry()));
        Glide.with(this).load(weatherObject.getPicture()).into(picture);
        description.setText(weatherObject.getDescription());
        avgTemp.setText(weatherObject.getAvgTemp()+"");
        minTemp.setText(weatherObject.getMinTemp()+"");
        maxTemp.setText(weatherObject.getMaxTemp()+"");
        pressure.setText(weatherObject.getPressure()+"");
        wind.setText(weatherObject.getWind()+"");
        humidity.setText(weatherObject.getHumidity()+"");
        sunsetTime.setText(weatherObject.getSunsetTime());
        sunriseTime.setText(weatherObject.getSunriseTime());
        status.setText(weatherObject.getStatus());
    }
}
