package com.coffeetime;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.coffeetime.admin.AdminActivity;
import com.coffeetime.client.MainClientActivity;
import com.coffeetime.warkop.MainWarkopActivity;

public class SplashScreenActivity extends Activity {

    SharedPreferences sharedPreferences;
    int tipe_user;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        sharedPreferences = getSharedPreferences("coffee",0);
        tipe_user = sharedPreferences.getInt("login",-1);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (tipe_user) {
                    case -1:
                        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(intent);

                        break;
                    case 0:
                        Intent intent1 = new Intent(SplashScreenActivity.this, MainWarkopActivity.class);
                        startActivity(intent1);

                        break;
                    case 1:
                        Intent intent2 = new Intent(SplashScreenActivity.this, MainClientActivity.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(SplashScreenActivity.this, AdminActivity.class);
                        startActivity(intent3);
                        break;
                }
                finish();
            }
        },2000);

    }
}