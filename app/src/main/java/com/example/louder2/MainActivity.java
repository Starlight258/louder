package com.example.louder2;

import static java.sql.DriverManager.println;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.louder2.Fragment.FragHome;
import com.example.louder2.Fragment.FragMap;
import com.example.louder2.Fragment.FragSetting;
import com.example.louder2.Fragment.FragSound;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private GoogleMap mMap;
    //int notinum=0;
    //?????? ???????????????
    BottomNavigationView bottomNavigationView;
    private String TAG = "??????";

    //??????????????? ??????
    Fragment fragment_home, fragment_sound, fragment_map, fragment_settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Louder");

        //??????
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
        //??????????????? ??????
        fragment_home = new FragHome();
        fragment_sound = new FragSound();
        fragment_map = new FragMap();
        fragment_settings = new FragSetting();
        //?????? ???????????????
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_home).commitAllowingStateLoss();
        // ????????? ??????
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i(TAG, "?????? ??????????????? ??????");

                switch (item.getItemId()) {
                    case R.id.homeItem:
                        Log.i(TAG, "home ?????????");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_home).commitAllowingStateLoss();
                        return true;
                    case R.id.soundItem:
                        Log.i(TAG, "sound ?????????");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_sound).commitAllowingStateLoss();
                        return true;
                    case R.id.mapItem:
                        Log.i(TAG, "map ?????????");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_map).commitAllowingStateLoss();
                        return true;
                    case R.id.settingItem:
                        Log.i(TAG, "setting ?????????");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, new FragSetting()).commit();
                        return true;

                }

                return true;
            }
        });
        //FCM ????????????
        Intent fcm = new Intent(getApplicationContext(), MyFirebaseMessaging.class);
        startService(fcm);
        Log.i("info", "FCM ????????? ??????");

//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        String msg = task.getResult();
//                        Log.println(Log.INFO,"token", msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });


    }
//
//    //?????? ??????
//    public void createNotification(View view) {
//        show();
//        Toast.makeText(this, "?????? ?????? ??????", Toast.LENGTH_LONG).show();
//    }
//
//    private void show() {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
//
//        builder.setSmallIcon(R.mipmap.ic_launcher); //?????? ?????????
//        builder.setContentTitle("?????? ??????");
//        builder.setContentText("?????? ?????? ?????????");
//
//        Intent intent = new Intent(this, FragHome.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE); //FLAG_MUTABLE ?????? IMMUTABLE ??? ??????
//        builder.setContentIntent(pendingIntent);
//
//        //??? ?????????
//        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.siren);
//        builder.setLargeIcon(largeIcon);
//
//        //??? ??????
//        builder.setColor(Color.RED);
//
//        //?????????
//        Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
//        builder.setSound(ringtoneUri);
//
//        //??????
//        long[] vibrate = {0, 100, 200, 300};
//        builder.setVibrate(vibrate);
//        builder.setAutoCancel(true); //???????????? ?????? ????????? ?????? ??????
//
//
//        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        //????????? ??????
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            manager.createNotificationChannel(new NotificationChannel("default", "?????? ??????", NotificationManager.IMPORTANCE_DEFAULT));
//        }
//        //???????????? ????????? id?????? ????????????(Object ???????????? ??? id????????? ??????)
//        manager.notify(notinum++, builder.build());
//    }
//    //?????? ?????? ??????
//    public void removeNotificaton(View view){
//        hide();
//    }
//    public void hide(){
//        //id ?????? noti ??????
//        if(notinum>=0){
//            NotificationManagerCompat.from(this).cancel(notinum--);
//        }
//    }

    public void onFragmentChange(int index){
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_map).commit();
        }else if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_sound).commit();
        }
    }
}