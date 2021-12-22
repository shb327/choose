package com.example.choose.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import com.example.choose.CreatePost;
import com.example.choose.R;
import com.example.choose.RetrofitUtils;
import com.example.choose.api.LoginController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn = findViewById(R.id.login_button);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        TextView txt = findViewById(R.id.textView9);

        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        LoginController controller = RetrofitUtils.getInstance().getRetrofit().create(LoginController.class);
        btn.setOnClickListener(v -> {
            controller.login(email.getText().toString(), password.getText().toString())
                    .enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.i("post", response.raw().request().headers().toString());
                            if (response.code() == 200) {
                                startActivity(new Intent(LoginActivity.this, CreatePost.class));
                            } else {
                                email.setTextColor(Color.parseColor("#F75010"));
                                email.setHintTextColor(Color.parseColor("#F75010"));
                                email.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_round_highlight_off_24, 0);
                                password.setTextColor(Color.parseColor("#F75010"));
                                password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_round_highlight_off_24, 0);
                                password.setHintTextColor(Color.parseColor("#F75010"));
                                txt.setTextColor(Color.parseColor("#F75010"));
                                txt.startAnimation(shake);
                                email.startAnimation(shake);
                                password.startAnimation(shake);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("Login Error", t.getMessage(), t);
                        }
                    });
        });
    }
}