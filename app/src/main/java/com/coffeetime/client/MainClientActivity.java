package com.coffeetime.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.coffeetime.Config;
import com.coffeetime.LoginActivity;
import com.coffeetime.R;
import com.coffeetime.helper.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainClientActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);

        bundle = getIntent().getExtras();

        // kita set default nya Home Fragment
        // inisialisasi BottomNavigaionView
        BottomNavigationView navigation = findViewById(R.id.navigation);
        // beri listener pada saat item/menu bottomnavigation terpilih
        navigation.setOnNavigationItemSelectedListener(this);

        //mTextMessage = (TextView) findViewById(R.id.message);
        //client_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        if(bundle!=null)
        {
            if(bundle.getBoolean("set_history"))
            {

                navigation.getMenu().findItem(R.id.navigation_riwayat).setChecked(true);
                loadFragment(new RiwayatFragment());
            }
            else
            {
                loadFragment(new HomeFragment());
            }
        }
        else
        {
            loadFragment(new HomeFragment());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        Bundle data = new Bundle();

        switch (item.getItemId()){
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_riwayat:
                fragment = new RiwayatFragment();
                break;
            case R.id.navigation_profil:
                fragment = new ProfilClientFragment();
                data.putString("a","tes");
                fragment.setArguments(data);
                break;
        }   return loadFragment(fragment);
    }

    // method untuk load fragment yang sesuai
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }   return false;
    }

    public void biji(View view) {
        startActivity(new Intent(MainClientActivity.this, ListKopiBijiActivity.class));
    }

    public void bubuk(View view) {
        startActivity(new Intent(MainClientActivity.this, ListKopiBubukActivity.class));
    }

    public void kopijadi(View view) {
        startActivity(new Intent(MainClientActivity.this, ListKopiJadiActivity.class));
    }

    public void terfavorit(View view) {
        Intent favorite = new Intent(MainClientActivity.this, ListWarkopActivity.class);
        favorite.putExtra("title", "Wakrop Favorit");
        favorite.putExtra("code", "fav");

        startActivity(favorite);
    }

    public void terbaru (View view) {
        Intent favorite = new Intent(MainClientActivity.this, ListWarkopActivity.class);
        favorite.putExtra("title", "Wakrop Terbaru");
        favorite.putExtra("code", "new");

        startActivity(favorite);
    }

    public void terlaris (View view) {
        Intent favorite = new Intent(MainClientActivity.this, ListWarkopActivity.class);
        favorite.putExtra("title", "Wakrop Terlaris");
        favorite.putExtra("code", "sell");

        startActivity(favorite);
    }

    public void Logout(View view) {
        startActivity(new Intent(MainClientActivity.this, LoginActivity.class));
    }

    //    MENU
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu,add items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu_client, menu);//Menu ResourceFile
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item1:

                Intent order = new Intent(getApplicationContext(), OrderanUser.class);
                startActivity(order);
                finish();

                Toast.makeText(getApplicationContext(), "ORDERAN", Toast.LENGTH_LONG).show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
