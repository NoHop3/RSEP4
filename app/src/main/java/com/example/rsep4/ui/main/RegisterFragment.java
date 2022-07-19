package com.example.rsep4.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rsep4.R;
import com.example.rsep4.models.UserModel;
import com.example.rsep4.viewmodels.AuthenticationViewModel;
import com.google.android.material.snackbar.Snackbar;

public class RegisterFragment extends Fragment {
    private AuthenticationViewModel viewModel;
    private TextView goToLogin;
    private Button btnRegister;
    private EditText username;
    private EditText password;
    private EditText email;

    public static RegisterFragment newInstance() {return new RegisterFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Setting all layouts
        username = view.findViewById(R.id.usernameR);
        password = view.findViewById(R.id.passwordR);
        email = view.findViewById(R.id.email);
        btnRegister = view.findViewById(R.id.btnRegister);
        goToLogin = view.findViewById(R.id.goToLogin);

        // Fragment logic
        viewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        btnRegister.setOnClickListener(view1 -> {

            try {
                viewModel.register(new UserModel(username.getText().toString(), password.getText().toString(), email.getText().toString()));

                if(this.getActivity() != null) {
                    this.getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(this.getId(), WeatherFragment.newInstance())
                            .commitNow();
                }
                else
                {
                    Snackbar.make(view, "Unable to register.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                }
            }
            catch (Exception e)
            {
                Log.e("error register", e.getMessage());
            }

        });
        goToLogin.setOnClickListener(view1 -> {
            if(this.getActivity() != null) {
                this.getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(this.getId(), LoginFragment.newInstance())
                        .commitNow();
            }
            else
            {
                Snackbar.make(view, "Unable to go to login.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });

        return view;
    }
}
