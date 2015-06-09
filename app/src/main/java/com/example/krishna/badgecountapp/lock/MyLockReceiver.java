package com.example.krishna.badgecountapp.lock;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyLockReceiver extends DeviceAdminReceiver {
    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Log.d("MyLockReceiver", "onDisabled (Line:16) :");
        Toast.makeText(context, "message"+"Disabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        Log.d("MyLockReceiver", "onEnabled (Line:21) :");
        Toast.makeText(context, "message"+"enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        Log.d("MyLockReceiver", "onDisableRequested (Line:30) :");
        Toast.makeText(context, "message"+"DisableRequested", Toast.LENGTH_SHORT).show();
        return "Needs to unlock";
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("MyLockReceiver", "onReceive (Line:13) :");
        Toast.makeText(context, "receiver", Toast.LENGTH_SHORT).show();
    }
}
