package com.example.rsep4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.rsep4.ui.main.LoginFragment;
import com.example.rsep4.ui.main.WeatherFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow();
        }
    }
}