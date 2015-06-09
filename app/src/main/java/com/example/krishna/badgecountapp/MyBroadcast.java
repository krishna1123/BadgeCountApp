package com.example.krishna.badgecountapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcast extends BroadcastReceiver {
    public MyBroadcast() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        try{
            String data=intent.getStringExtra("val");
            Toast.makeText(context, "MyCustomBroadcast BadgeCount------"+data, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(context, "MyCustomBroadcast BadgeCount", Toast.LENGTH_SHORT).show();
    }
}

