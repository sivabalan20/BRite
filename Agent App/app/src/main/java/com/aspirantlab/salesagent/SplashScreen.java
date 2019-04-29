package com.aspirantlab.salesagent;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


public class SplashScreen extends Activity {
    String MY_PREFS_NAME;
    boolean Loginstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        /****** Create Thread that will sleep for 5 seconds****/
        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(10*1000);
                    SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    Loginstatus = prefs.getBoolean("loginstatus", false);
                    // After 5 seconds redirect to another intent
                    if(Loginstatus){
                        Intent i=new Intent(getBaseContext(),MainActivity.class);
                        startActivity(i);

                    }else{
                        Intent i=new Intent(getBaseContext(),LoginActivity.class);
                        startActivity(i);
                    }


                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }
    @Override
    public void onBackPressed() {
        // do nothing.
    }
}
