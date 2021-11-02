package com.example.choose;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TextPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_post2);


        Button button = findViewById(R.id.sendBtn);
        EditText editText1 = findViewById(R.id.titleTxt);
        EditText editText2 = findViewById(R.id.contentTxt);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(() -> {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://127.0.0.1:8080/api/posts")
                            .post(new FormBody.Builder()
                                .add("title", editText1.getText().toString())
                                .add("content", editText2.getText().toString())
                                    .build())
                            .build();
                    Call call = okHttpClient.newCall(request);
                    try {
                        Response response = call.execute();
                        Log.d("textpost", response.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }
}