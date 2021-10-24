package com.example.choose;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.widget.TextView;

public class ChooseType extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);

        textView = (TextView) findViewById(R.id.textView4);
        textView.setText("Choose");
        textView.setTextColor(Color.parseColor("#329D9C"));

        TextPaint paint = textView.getPaint();
        float width = paint.measureText("Choose");

        Shader textShader = new LinearGradient(0, width, 0, 0,
                new int[]{
                        Color.parseColor("#329D9C"),
                        Color.parseColor("#7BE495"),
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);
    }
}