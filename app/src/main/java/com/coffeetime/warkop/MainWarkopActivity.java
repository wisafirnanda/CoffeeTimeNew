package com.coffeetime.warkop;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.coffeetime.Config;
import com.coffeetime.LoginActivity;
import com.coffeetime.R;
import com.coffeetime.client.MainClientActivity;
import com.coffeetime.helper.NotificationUtils;
import com.coffeetime.model.Warkop;
import com.coffeetime.networkmanager.Connection;
import com.coffeetime.networkmanager.Endpoints;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainWarkopActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    Animation animRotate;
    ImageView imgRotate;

    Warkop warkop;
    Endpoints endpoints;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String TAG = MainClientActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_warkop);

        imgRotate = findViewById(R.id.imgAnim);

        // kita set default nya Home Fragment
        loadFragment(new MenuFragment());
        // inisialisasi BottomNavigaionView
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_warkop);
        // beri listener pada saat item/menu bottomnavigation terpilih
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        sharedPreferences = getSharedPreferences("coffee", 0);
        editor = sharedPreferences.edit();
        editor.apply();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };

        displayFirebaseRegId();


    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container_warkop, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.navigation_menu:
                fragment = new MenuFragment();
                break;
            case R.id.navigation_pesanan:
                fragment = new PesananFragment();
                break;
            case R.id.navigation_profil:
                fragment = new ProfilWarkopFragment();
                break;
        }
        return loadFragment(fragment);
    }

    public void ProfilWarkop(View view) {
        startActivity(new Intent(MainWarkopActivity.this, TambahWarkopActivity.class));
    }

    //    MENU
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu,add items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu, menu);//Menu ResourceFile
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item1:
//                Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();

                animRotate = AnimationUtils.loadAnimation(MainWarkopActivity.this,R.anim.rotate);
                imgRotate.setVisibility(View.VISIBLE);
                imgRotate.startAnimation(animRotate);

                int _TIMER = 1000;
                Handler handler = new Handler();

                // Timer
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = getIntent();
                        startActivity(intent);
                    }
                }, _TIMER);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
