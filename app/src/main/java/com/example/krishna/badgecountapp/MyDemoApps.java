package com.example.krishna.badgecountapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.krishna.badgecountapp.animation.AccelerometerPlayActivity;
import com.example.krishna.badgecountapp.animation.CompassActivity;
import com.example.krishna.badgecountapp.animation.CubeWallpaper2Settings;
import com.example.krishna.badgecountapp.animation.LiveWallSettings;
import com.example.krishna.badgecountapp.test.CaptureActivity;
import com.example.krishna.badgecountapp.test.TestActivity;
import com.example.krishna.badgecountapp.views.ImageActivity;


public class MyDemoApps extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_demo_apps);

        setOnClickListeners(
                R.id.btn_badgecount,
                R.id.btn_compass,
                R.id.btn_rotation_orientation,
                R.id.btn_Adil,
                R.id.btn_Messager,
                R.id.btn_setWallPaper,
                R.id.btn_nfc,
                R.id.btn_save_image,
                R.id.btn_image_capture,
                R.id.btn_image_merging,
                R.id.btn_print_content,
                R.id.btn_send_email,
                R.id.btn_screen_record,
                R.id.btn_live_wallpaper,
                R.id.btn_live_wallpaper_settings,
                R.id.btn_accellaration,
                R.id.btn_imageLoader,
                R.id.btn_test_sample
                );
    }

    void setOnClickListeners(int... ids){
        for(int id: ids){
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.btn_badgecount:

                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                intent=new Intent(this, MainActivity.class);
                break;
            case R.id.btn_compass:
                intent =new Intent(this, CompassActivity.class);
                break;
            case R.id.btn_rotation_orientation:
                intent =new Intent(this, RotationAnimation.class);
                break;
            case R.id.btn_Adil:
                intent=new Intent(this, SampleAidlActivity.class);
                break;
            case R.id.btn_Messager:
                intent=new Intent(this, SampleMessagerActivity.class);
                break;
            case R.id.btn_setWallPaper:
                intent=new Intent(this, SetWallpaperActivity.class);
                break;
            case R.id.btn_nfc:
                intent=new Intent(this, NfcMainActivity.class);
                break;
            case R.id.btn_save_image:
                intent=new Intent(this, SaveImageActivity.class);
                break;
            case R.id.btn_image_capture:
                intent=new Intent(this, CaptureActivity.class);
                break;
            case R.id.btn_print_content:
                intent=new Intent(this, PrintCustomContent.class);
                break;
            case R.id.btn_image_merging:
                intent=new Intent(this, ImageActivity.class);
                break;
            case R.id.btn_send_email:
                intent=new Intent(this, SendEmailActivity.class);
                break;
            case R.id.btn_screen_record:
                intent=new Intent(this, ScreenRecordFragment.class);
                break;
            case R.id.btn_live_wallpaper:
                intent=new Intent(this, CubeWallpaper2Settings.class);
                break;
            case R.id.btn_live_wallpaper_settings:
                intent=new Intent(this, LiveWallSettings.class);
                break;
            case R.id.btn_test_sample:
                intent=new Intent(this, TestActivity.class);
                break;
            case R.id.btn_accellaration:
                intent=new Intent(this, AccelerometerPlayActivity.class);
                break;
            case R.id.btn_imageLoader:
                intent=new Intent(this, ImageLoaderActivity.class);
                break;
            default:
                return;
        }

        if(intent!=null) {
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }
}
