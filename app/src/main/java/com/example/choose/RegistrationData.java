package com.example.choose;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.choose.ui.login.LoginActivity;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.kofigyan.stateprogressbar.components.StateItem;
import com.kofigyan.stateprogressbar.listeners.OnStateItemClickListener;

public class RegistrationData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_data);

        StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
        TextView txt = (TextView)findViewById(R.id.login_btn);
        Button btn = (Button)findViewById(R.id.next_one_btn);

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationData.this, LoginActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationData.this, RegistrationPassword.class));
            }
        });

        stateProgressBar.setOnStateItemClickListener(new OnStateItemClickListener() {
            @Override
            public void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState) {
                if(stateNumber == 1){
                    startActivity(new Intent(RegistrationData.this, RegistrationUsername.class));
                }
                if(stateNumber == 2){
                    startActivity(new Intent(RegistrationData.this, RegistrationEmail.class));
                }
                if(stateNumber == 3){
                    startActivity(new Intent(RegistrationData.this, RegistrationVarification.class));
                }
            }
        });
    }
}