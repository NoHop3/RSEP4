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

public class ForgotPasswordFragment extends Fragment {
    private AuthenticationViewModel viewModel;
    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private Button btnUpdate;
    private TextView goToRegister;

    public static ForgotPasswordFragment newInstance() {return new ForgotPasswordFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        // Setting all layouts
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        goToRegister = view.findViewById(R.id.goToRegister);

        // Fragment logic
        viewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        btnUpdate.setOnClickListener(view1 -> {

            try {
                if(confirmPassword.getText().toString().equalsIgnoreCase(password.getText().toString()))
                {
                    viewModel.updateUser(username.getText().toString(),new UserModel(username.getText().toString(), password.getText().toString()));
                }
                else {
                    Snackbar.make(view, "Password must be identical", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                }
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

        return view;
    }
}
