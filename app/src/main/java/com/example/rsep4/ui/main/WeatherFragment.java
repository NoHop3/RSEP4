package com.example.rsep4.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rsep4.R;
import com.example.rsep4.adapter.WeatherAdapter;
import com.example.rsep4.models.WeatherModel;
import com.example.rsep4.viewmodels.WeatherDetailsViewModel;
import com.example.rsep4.viewmodels.WeatherViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class WeatherFragment extends Fragment implements WeatherAdapter.ItemClickListener {

    private WeatherViewModel mViewModel;
    private WeatherDetailsViewModel detailsViewModel;
    RecyclerView recyclerView;
    TextView textViewNoResult;
    WeatherAdapter adapter;
    List<WeatherModel> weatherList;
    ProgressBar loader;
    FloatingActionButton fabAdd;

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.weatherRecyclerView);
        textViewNoResult = view.findViewById(R.id.textViewNoResult);
        fabAdd = view.findViewById(R.id.fabAdd);
        loader = view.findViewById(R.id.loader);

        // Fragment logic
        loader.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WeatherAdapter(view.getContext(), weatherList, this);
        recyclerView.setAdapter(adapter);
        mViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        detailsViewModel = new ViewModelProvider(getActivity()).get(WeatherDetailsViewModel.class);
        mViewModel.getWeatherListObserver().observe(getViewLifecycleOwner(), weatherModels -> {
             if(weatherModels != null) {
                 loader.setVisibility(View.GONE);
                 weatherList = weatherModels;
                 adapter.setWeatherList(weatherModels);
                 textViewNoResult.setVisibility(View.GONE);
             }
             else {
                 loader.setVisibility(View.GONE);
                textViewNoResult.setVisibility(View.VISIBLE);
             }
        });
        mViewModel.getAllWeather();

        // FAB onclick -> Should go to add a new location fragment
        fabAdd.setOnClickListener( view1 -> {
        if(this.getActivity() != null) {
            detailsViewModel.resetDetails();
            this.getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(this.getId(), WeatherModifyFragment.newInstance())
                    .commitNow();
        }
        else
        {
            Snackbar.make(view, "Unable to go to add a weather fragment", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show();
        }
        });

        return view;
    }

    @Override
    public void onWeatherClick(WeatherModel item) {
        try {
            detailsViewModel.setCity(item.getCity());
            Log.v("city", detailsViewModel.getCity());
            if(this.getActivity() != null) {
                this.getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(this.getId(), WeatherDetailsFragment.newInstance())
                        .commitNow();
            }
        }
        catch (Exception e)
        {
            Log.e("error in setting city", e.getMessage());
        }
    }

}