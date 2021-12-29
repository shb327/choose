package com.example.choose.create;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.choose.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class PlayOffPost extends AppCompatActivity {

    TextView selected;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_off);

        TextInputLayout titleLayout = findViewById(R.id.titleLayout);
        TextInputEditText editText1 = findViewById(R.id.titleTxt);

        Button nextButton = findViewById(R.id.nextBtn);

        TextView options8 = findViewById(R.id.options8);
        TextView options16 = findViewById(R.id.options16);
        TextView options32 = findViewById(R.id.options32);
        TextView options64 = findViewById(R.id.options64);
        TextView options128 = findViewById(R.id.options128);
        TextView options256 = findViewById(R.id.options256);

        final Typeface bold = ResourcesCompat.getFont(this, R.font.montserrat_bold);
        final Typeface regular = ResourcesCompat.getFont(this, R.font.montserrat);
        selected = options8;

        options8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setTypeface(regular);
                System.out.println(selected.getText() + "set regular text");
                options8.setTypeface(bold);
                selected = options8;
                System.out.println("new selected: " + selected.getText() + " bold text");
            }
        });

        options16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setTypeface(regular);
//                selected.setTextSize(20);
                options16.setTypeface(bold);
//                options16.setTextSize(22);
                selected = options16;
            }
        });

        options32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setTypeface(regular);
//                selected.setTextSize(20);
                System.out.println(selected.getText() + "set regular text");
                options32.setTypeface(bold);
//                options32.setTextSize(22);
                selected = options32;
                System.out.println("new selected: " + selected.getText() + " bold text");}
        });

        options64.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setTypeface(regular);
//                selected.setTextSize(20);
                System.out.println(selected.getText() + "set regular text");
                options64.setTypeface(bold);
//                options64.setTextSize(22);
                selected = options64;
                System.out.println("new selected: " + selected.getText() + " bold text");
            }
        });

        options128.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setTypeface(regular);
//                selected.setTextSize(20);

                System.out.println(selected.getText() + "set regular text");

                options128.setTypeface(bold);
//                options128.setTextSize(22);

                selected = options128;
                System.out.println("new selected: " + selected.getText() + " bold text");
            }
        });

        options256.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setTypeface(regular);
//                selected.setTextSize(20);

                System.out.println(selected.getText() + "set regular text");

                options256.setTypeface(bold);
//                options256.setTextSize(22);

                selected = options256;
                System.out.println("new selected: " + selected.getText() + " bold text");
            }
        });

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText1.getText().length() == 21) {
                    titleLayout.setErrorEnabled(true);
                    titleLayout.setError("Character limit exceeded");
                    editText1.setTextColor(Color.parseColor("#F75010"));

                } else {
                    titleLayout.setErrorEnabled(false);
                    titleLayout.setError(null);
                    editText1.setTextColor(Color.parseColor("#68B2A0"));
                    titleLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                    titleLayout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.getText().length() == 0) {
                    titleLayout.setErrorEnabled(true);
                    titleLayout.setError("The title cannot be empty");
                    titleLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    editText1.setTextColor(Color.parseColor("#F75010"));
                    System.out.println("the title length is 0");
                    return;
                }
                else if (titleLayout.isErrorEnabled()) {
                    System.out.println("error in title");
                    return;
                }
            }
        });

    }


}
