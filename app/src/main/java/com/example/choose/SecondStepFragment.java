package com.example.choose;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.choose.R;
import com.example.choose.api.RegistrationController;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SecondStepFragment extends Fragment {

    TextInputLayout email;
    TextInputEditText field;

    TextInputLayout first;
    TextInputEditText name;

    TextInputLayout last;
    TextInputEditText surname;

    RegistrationController registrationController;

    public SecondStepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registrationController = RetrofitUtils.getInstance().getRetrofit().create(RegistrationController.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.outlinedTextField1);
        field = view.findViewById(R.id.email);

        first = view.findViewById(R.id.outlinedTextField2);
        name = view.findViewById(R.id.hidden);

        last = view.findViewById(R.id.outlinedTextField3);
        surname = view.findViewById(R.id.second);

        field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(!field.getText().toString().matches("^[a-zA-Z0-9_!#$%&’*+\\=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")){
                    email.setErrorEnabled(true);
                    email.setError("Invalid Email Adress");
                    field.setTextColor(Color.parseColor("#F75010"));
                }else {
                    email.setErrorEnabled(false);
                    email.setError(null);
                    field.setTextColor(Color.parseColor("#56C596"));
                }
            }
        });
    }
}