package com.example.choose;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;

import com.example.choose.api.PostController;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ImagePost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_post);
        TextInputEditText descriptionTxt = findViewById(R.id.descriptionTxt);
        TextInputLayout descriptionLayout = findViewById(R.id.descriptionLayout);
        TextInputEditText titleTxt = findViewById(R.id.titleTxt);
        TextInputLayout titleLayout = findViewById(R.id.titleLayout);

        descriptionTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(descriptionTxt.getText().length()>256){
                    descriptionLayout.setErrorEnabled(true);
                    descriptionLayout.setError("Character limit exceeded");
                    descriptionTxt.setTextColor(Color.parseColor("#F75010"));
                }
                else {
                    descriptionLayout.setErrorEnabled(false);
                    descriptionLayout.setError(null);
                    descriptionTxt.setTextColor(Color.parseColor("#68B2A0"));
                }
            }
        });

        titleTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(titleTxt.getText().length()==20){
                    titleLayout.setErrorEnabled(true);
                    titleLayout.setError("Character limit exceeded");
                    titleTxt.setTextColor(Color.parseColor("#F75010"));
                }
                else {
                    titleLayout.setErrorEnabled(false);
                    titleLayout.setError(null);
                    titleTxt.setTextColor(Color.parseColor("#68B2A0"));
                }
            }
        });

        FloatingActionButton btn2 = findViewById(R.id.fab_start);
        btn2.setOnClickListener(v -> startActivity(new Intent(ImagePost.this, ChooseType.class)));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        MenuItem item = bottomNavigationView.getMenu().findItem(R.id.profile);
        item.setOnMenuItemClickListener(item1 -> {
            startActivity(new Intent(ImagePost.this, CreatePost.class));
            return false;
        });
    }
}