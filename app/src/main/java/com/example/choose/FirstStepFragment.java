package com.example.choose;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.choose.R;
import com.example.choose.api.LoginController;
import com.example.choose.api.RegistrationController;
import com.example.choose.dto.RegistrationUsernameDTO;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstStepFragment extends Fragment {

    public FirstStepFragment() { }

    TextInputLayout username;
    TextInputEditText field;

    TextInputLayout password;
    TextInputEditText hidden;

    TextInputLayout confirm;
    TextInputEditText second;

    RegistrationController registrationController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registrationController = RetrofitUtils.getInstance().getRetrofit().create(RegistrationController.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = view.findViewById(R.id.outlinedTextField1);
        field = view.findViewById(R.id.username);

        password = view.findViewById(R.id.outlinedTextField2);
        hidden = view.findViewById(R.id.hidden);

        confirm = view.findViewById(R.id.outlinedTextField3);
        second = view.findViewById(R.id.second);

        field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(field.getText().length()== 20){
                    username.setErrorEnabled(true);
                    username.setError("Character Limit Exceeded");
                    field.setTextColor(Color.parseColor("#F75010"));
                }else if(!field.getText().toString().matches("^[a-zA-Z0-9._-]{3,}$")){
                    username.setErrorEnabled(true);
                    username.setError("Invalid Username");
                    field.setTextColor(Color.parseColor("#F75010"));
                }else {
                    username.setErrorEnabled(false);
                    username.setError(null);
                    field.setTextColor(Color.parseColor("#56C596"));
                }
            }
        });
        hidden.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(!hidden.getText().toString().matches("^(?=.*?[A-Z])(?=(.*[a-z])+)(?=(.*[\\d])+)(?=(.*[\\W])+)(?!.*\\s).{8,}$")){
                    password.setErrorEnabled(true);
                    password.setError("Invalid Password");
                    hidden.setTextColor(Color.parseColor("#F75010"));
                }else {
                    password.setErrorEnabled(false);
                    password.setError(null);
                    hidden.setTextColor(Color.parseColor("#56C596"));
                }
            }
        });
        second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(!second.getText().toString().equals(hidden.getText().toString())){
                    confirm.setErrorEnabled(true);
                    confirm.setError("Passwords Don't Match");
                    second.setTextColor(Color.parseColor("#F75010"));
                }else {
                    confirm.setErrorEnabled(false);
                    confirm.setError(null);
                    second.setTextColor(Color.parseColor("#56C596"));
                }
            }
        });
    }

    public void send(ViewPager viewPager){
        registrationController.username(
                new RegistrationUsernameDTO(field.getText().toString(), hidden.getText().toString()))
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 400) {
                            username.setErrorEnabled(true);
                            username.setError("Username Exists");
                            field.setTextColor(Color.parseColor("#F75010"));
                            return;
                        }
                        getActivity().runOnUiThread(() -> {
                            viewPager.setCurrentItem(1);
                        });
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public boolean isComplete() {
        return !username.isErrorEnabled() && !password.isErrorEnabled();
    }
}