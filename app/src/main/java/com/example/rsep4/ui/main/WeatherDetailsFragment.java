package com.example.rsep4.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class WeatherDetailsFragment extends Fragment {

    private WeatherModel weatherObject;
    private WeatherDetailsViewModel mViewModel;
    private ImageView picture;
    private TextView location;
    private TextView avgTemp;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView description;
    private TextView pressure;
    private TextView wind;
    private TextView humidity;
    private TextView sunsetTime;
    private TextView sunriseTime;
    private TextView status;
    private TextView updatedAt;
    private TextView errorText;
    private ProgressBar loader;
    FloatingActionButton fabEdit;
    FloatingActionButton fabBack;

    public static WeatherDetailsFragment newInstance() {
        return new WeatherDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // Setting all layouts
        loader = view.findViewById(R.id.loader);
        errorText = view.findViewById(R.id.errorText);
        picture= view.findViewById(R.id.picture);
        location= view.findViewById(R.id.location);
        avgTemp= view.findViewById(R.id.avgTemp);
        minTemp= view.findViewById(R.id.minTemp);
        maxTemp= view.findViewById(R.id.maxTemp);
        description= view.findViewById(R.id.description);
        pressure= view.findViewById(R.id.pressure);
        wind= view.findViewById(R.id.wind);
        humidity= view.findViewById(R.id.humidity);
        sunsetTime= view.findViewById(R.id.sunsetTime);
        sunriseTime= view.findViewById(R.id.sunriseTime);
        status= view.findViewById(R.id.status);
        updatedAt= view.findViewById(R.id.updatedAt);
        fabEdit = view.findViewById(R.id.fabEdit);
        fabBack = view.findViewById(R.id.fabBack);

        // Logic for WeatherDetailsFragment
        loader.setVisibility(View.VISIBLE);
        mViewModel = new ViewModelProvider(getActivity()).get(WeatherDetailsViewModel.class);

        Log.v("city", ""+mViewModel.getCity());
        mViewModel.getWeatherDetails(mViewModel.getCity());
        mViewModel.getWeatherObjectObserver().observe(getViewLifecycleOwner(), weatherModels -> {
            if(weatherModels != null) {
                loader.setVisibility(View.GONE);
                errorText.setVisibility(View.GONE);
                weatherObject = weatherModels;
                location.setText(String.format("%s, %s", weatherObject.getCity(), weatherObject.getCountry()));
                Glide.with(this).load(weatherObject.getPicture()).into(picture);
                description.setText(weatherObject.getDescription());
                avgTemp.setText(String.format("%s °C", weatherObject.getAvgTemp()));
                minTemp.setText(String.format("Min %s °C", weatherObject.getMinTemp()));
                maxTemp.setText(String.format("Max %s °C", weatherObject.getMaxTemp()));
                pressure.setText(String.format("%s p", weatherObject.getPressure()));
                wind.setText(String.format("%s m/s", weatherObject.getWind()));
                humidity.setText(String.format("%s %%", weatherObject.getHumidity()));
                sunsetTime.setText(weatherObject.getSunsetTime());
                sunriseTime.setText(weatherObject.getSunriseTime());
                updatedAt.setText(String.format("Last updated %s", weatherObject.getUpdatedAt()));

                status.setText(weatherObject.getStatus());
            }
            else {
                loader.setVisibility(View.GONE);
                errorText.setVisibility(View.VISIBLE);
            }
        });

        // FAB onclick -> should go to edit existing location fragment
        fabEdit.setOnClickListener( view1 ->
                {
                    if(this.getActivity() != null) {
                        this.getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(this.getId(), WeatherModifyFragment.newInstance())
                                .commitNow();
                    }
                    else
                    {
                        Snackbar.make(view, "Unable to go to edit a weather fragment", Snackbar.LENGTH_LONG)
                                .setAction("Action", null)
                                .show();
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
}
