package com.example.choose;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.choose.api.PostController;
import com.example.choose.dto.CreatePostRequestDTO;
import com.example.choose.dto.PostDTO;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TextPost extends AppCompatActivity {

    TextInputEditText editText1;
    TextInputEditText editText2;
    TextInputLayout titleLayout;
    TextInputLayout contentLayout;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_post);

        button = findViewById(R.id.sendBtn);
        editText1 = findViewById(R.id.titleTxt);
        editText2 = findViewById(R.id.contentTxt);
        titleLayout = findViewById(R.id.titleLayout);
        contentLayout = findViewById(R.id.contentLayout);
        titleLayout.setErrorEnabled(true);
        contentLayout.setErrorEnabled(true);
        titleLayout.setCounterTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

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


        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText2.getText().length() > 256) {
                    contentLayout.setErrorEnabled(true);
                    contentLayout.setError("Character limit exceeded");
                    editText2.setTextColor(Color.parseColor("#F75010"));
                } else {
                    contentLayout.setErrorEnabled(false);
                    contentLayout.setError(null);
                    editText2.setTextColor(Color.parseColor("#68B2A0"));
                    contentLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                    contentLayout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                    contentLayout.setCounterTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                }
            }
        });

        FloatingActionButton btn2 = findViewById(R.id.fab_start);
        btn2.setOnClickListener(v -> startActivity(new Intent(TextPost.this, ChooseType.class)));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        MenuItem item = bottomNavigationView.getMenu().findItem(R.id.profile);
        PostController postController = RetrofitUtils.getInstance().getRetrofit().create(PostController.class);
        item.setOnMenuItemClickListener(item1 -> {
            startActivity(new Intent(TextPost.this, CreatePost.class));
            return false;
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((editText1.getText().length() == 0) && (editText2.getText().length() == 0)) {
                    titleLayout.setErrorEnabled(true);
                    titleLayout.setError("The title cannot be empty");
                    titleLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    editText1.setTextColor(Color.parseColor("#F75010"));
                    contentLayout.setErrorEnabled(true);
                    contentLayout.setError("The description cannot be empty");
                    contentLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    editText2.setTextColor(Color.parseColor("#F75010"));
                    contentLayout.setCounterTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    return;
                } else if (contentLayout.isErrorEnabled()) { return;
                } else if (titleLayout.isErrorEnabled()) { return;
                } else if (editText1.getText().length() == 0) {
                    titleLayout.setErrorEnabled(true);
                    titleLayout.setError("The title cannot be empty");
                    titleLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    editText1.setTextColor(Color.parseColor("#F75010"));
                    return;
                } else if (editText2.getText().length() == 0) {
                    contentLayout.setErrorEnabled(true);
                    contentLayout.setError("The description cannot be empty");
                    contentLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    editText2.setTextColor(Color.parseColor("#F75010"));
                    contentLayout.setCounterTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    return;
                }

                postController.createPost(new CreatePostRequestDTO(
                        editText2.getText().toString(),
                        editText1.getText().toString()))
                        .enqueue(new Callback<PostDTO>() {
                            @Override
                            public void onResponse(Call<PostDTO> call, Response<PostDTO> response) {
                                startActivity(new Intent(TextPost.this, CreatePost.class));
                            }

                            @Override
                            public void onFailure(Call<PostDTO> call, Throwable t) {
                                Toast.makeText(TextPost.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}