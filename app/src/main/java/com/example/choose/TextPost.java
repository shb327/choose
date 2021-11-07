package com.example.choose;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.choose.service.PostService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TextPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_post2);


        Button button = findViewById(R.id.sendBtn);
        EditText editText1 = findViewById(R.id.titleTxt);
        EditText editText2 = findViewById(R.id.contentTxt);

        button.setOnClickListener(v -> {
            PostService.getInstance()
                    .createPost(Long.valueOf(editText1.getText().toString()),
                                editText2.getText().toString())
            .enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {
                    Log.e("post", t.getMessage(), t);
                }
            });
//                AsyncTask.execute(() -> {
//                    OkHttpClient okHttpClient = new OkHttpClient();
//                    Request request = new Request.Builder()
//                            .url("http://127.0.0.1:8080/api/posts")
//                            .post(new FormBody.Builder()
//                                .add("title", editText1.getText().toString())
//                                .add("content", editText2.getText().toString())
//                                    .build())
//                            .build();
//                    Call call = okHttpClient.newCall(request);
//                    try {
//                        Response response = call.execute();
//                        Log.d("textpost", response.toString());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
        });
    }
}