package com.example.champ.houseserviceadmin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences pref;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        pref= getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String strng= pref.getString("login","");

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                if (strng.equals("1")){
                    Intent intent= new Intent(SplashScreen.this,Categories.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },3000);

    }
}
