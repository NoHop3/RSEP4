package com.example.rsep4.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rsep4.R;
import com.example.rsep4.adapter.WeatherAdapter;
import com.example.rsep4.models.WeatherModel;
import com.example.rsep4.viewmodels.WeatherViewModel;

import java.util.List;

public class WeatherFragment extends Fragment {

    private WeatherViewModel mViewModel;
    RecyclerView recyclerView;
    TextView textViewNoResult;
    WeatherAdapter adapter;
    List<WeatherModel> weatherList;

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.weatherRecyclerView);
        textViewNoResult = view.findViewById(R.id.textViewNoResult);
        LinearLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WeatherAdapter(view.getContext(), weatherList);
        recyclerView.setAdapter(adapter);
        mViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        mViewModel.getWeatherListObserver().observe(getViewLifecycleOwner(), weatherModels -> {
             if(weatherModels != null) {
                 weatherList = weatherModels;
                 adapter.setWeatherList(weatherModels);
                 textViewNoResult.setVisibility(View.GONE);
             }
             else {
                textViewNoResult.setVisibility(View.VISIBLE);
             }
        });
        mViewModel.getAllWeather();
        return view;
    }
}