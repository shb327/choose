package com.example.choose;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.choose.home.HomeActivity;

public class PostDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_display);

        TextView title = findViewById(R.id.textView10);
        TextView desc = findViewById(R.id.textView11);
        Button button = findViewById(R.id.sendBtn);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            title.setText(extras.getString("title"));
            desc.setText(extras.getString("desc"));
        }

        button.setOnClickListener(v -> startActivity(new Intent(PostDisplay.this, HomeActivity.class)));
    }
}