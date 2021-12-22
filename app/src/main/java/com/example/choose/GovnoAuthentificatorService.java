package com.example.choose;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class GovnoAuthentificatorService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new GovnoAuthentificator(this).getIBinder();
    }
}
