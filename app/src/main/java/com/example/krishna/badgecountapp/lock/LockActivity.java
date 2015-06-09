package com.example.krishna.badgecountapp.lock;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.krishna.badgecountapp.R;

public class LockActivity extends Activity {
    private final static String LOG_TAG = "DevicePolicyAdmin";
    DevicePolicyManager truitonDevicePolicyManager;
    ComponentName truitonDevicePolicyAdmin;
    private CheckBox truitonAdminEnabledCheckbox;
    protected static final int REQUEST_ENABLE = 1;
    protected static final int SET_PASSWORD = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        truitonDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        truitonDevicePolicyAdmin = new ComponentName(this,
                MyLockReceiver.class);

        truitonAdminEnabledCheckbox = (CheckBox) findViewById(R.id.checkBox);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isMyDevicePolicyReceiverActive()) {
            truitonAdminEnabledCheckbox.setChecked(true);
        } else {
            truitonAdminEnabledCheckbox.setChecked(false);
        }
        truitonAdminEnabledCheckbox
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        if (isChecked) {
                            Intent intent = new Intent(
                                    DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                            intent.putExtra(
                                    DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                                    truitonDevicePolicyAdmin);
                            intent.putExtra(
                                    DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                                    "explanation");
                            startActivityForResult(intent, REQUEST_ENABLE);
                        } else {
                            truitonDevicePolicyManager
                                    .removeActiveAdmin(truitonDevicePolicyAdmin);
                        }
                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ENABLE:
                    Log.v(LOG_TAG, "Enabling Policies Now");
                    truitonDevicePolicyManager.setMaximumTimeToLock(
                            truitonDevicePolicyAdmin, 30000L);
                    truitonDevicePolicyManager.setMaximumFailedPasswordsForWipe(
                            truitonDevicePolicyAdmin, 5);
                    truitonDevicePolicyManager.setPasswordQuality(
                            truitonDevicePolicyAdmin,
                            DevicePolicyManager.PASSWORD_QUALITY_COMPLEX);
                    truitonDevicePolicyManager.setCameraDisabled(
                            truitonDevicePolicyAdmin, true);
                    boolean isSufficient = truitonDevicePolicyManager
                            .isActivePasswordSufficient();
                    if (isSufficient) {
                        truitonDevicePolicyManager.lockNow();
                    } else {
                        Intent setPasswordIntent = new Intent(
                                DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
                        startActivityForResult(setPasswordIntent, SET_PASSWORD);
                        truitonDevicePolicyManager.setPasswordExpirationTimeout(
                                truitonDevicePolicyAdmin, 10000L);
                    }
                    break;
            }
        }
    }

    private boolean isMyDevicePolicyReceiverActive() {
        return truitonDevicePolicyManager
                .isAdminActive(truitonDevicePolicyAdmin);
    }
}