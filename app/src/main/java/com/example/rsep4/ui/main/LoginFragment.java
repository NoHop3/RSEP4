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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.rsep4.R;
import com.example.rsep4.models.UserModel;
import com.example.rsep4.viewmodels.AuthenticationViewModel;
import com.google.android.material.snackbar.Snackbar;

public class LoginFragment extends Fragment {
    private AuthenticationViewModel viewModel;
    private EditText username;
    private EditText password;
    private Button btnLogin;
    private TextView forgotPassword;
    private TextView goToRegister;

    public static LoginFragment newInstance() {return new LoginFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Setting all layouts
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        btnLogin = view.findViewById(R.id.btnLogin);
        forgotPassword = view.findViewById(R.id.forgotPassword);
        goToRegister = view.findViewById(R.id.goToRegister);

        // Fragment logic
        viewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        btnLogin.setOnClickListener(view1 -> {

            try {
                viewModel.login(new UserModel(username.getText().toString(), password.getText().toString()));
                if(this.getActivity() != null) {
                    this.getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(this.getId(), WeatherFragment.newInstance())
                            .commitNow();
                }
                else
                {
                    Snackbar.make(view, "Unable to login.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                }
            }
            catch (Exception e)
            {
                Log.e("error login", e.getMessage());
            }
        });
        goToRegister.setOnClickListener(view1 -> {
            if(this.getActivity() != null) {
                this.getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(this.getId(), RegisterFragment.newInstance())
                        .commitNow();
            }
            else
            {
                Snackbar.make(view, "Unable to go to register.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
        forgotPassword.setOnClickListener(view1 -> {
            if(this.getActivity() != null) {
                this.getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(this.getId(), ForgotPasswordFragment.newInstance())
                        .commitNow();
            }
            else
            {
                Snackbar.make(view, "Unable to change password", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });

        return view;
    }
}
