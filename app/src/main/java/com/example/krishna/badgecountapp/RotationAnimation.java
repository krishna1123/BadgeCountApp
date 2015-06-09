package com.example.krishna.badgecountapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

/**
 * Created by krishna on 12/5/15.
 */
public class RotationAnimation extends Activity {
    private int mRotationAnimation = WindowManager.LayoutParams.ROTATION_ANIMATION_ROTATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRotationAnimation(mRotationAnimation);
        setContentView(R.layout.rotation_animation);
        ((CheckBox)findViewById(R.id.cb_fullscreen)).setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        setFullscreen(isChecked);
                    }
                }
        );
        ((RadioGroup)findViewById(R.id.rd_group)).setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            default:
                            case R.id.radioButton1:
                                mRotationAnimation = WindowManager.LayoutParams.ROTATION_ANIMATION_ROTATE;
                                break;
                            case R.id.radioButton2:
                                mRotationAnimation = WindowManager.LayoutParams.ROTATION_ANIMATION_CROSSFADE;
                                break;
                            case R.id.radioButton3:
                                mRotationAnimation = WindowManager.LayoutParams.ROTATION_ANIMATION_JUMPCUT;
                                break;
                        }
                        setRotationAnimation(mRotationAnimation);
                    }
                }
        );
    }
    private void setFullscreen(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        } else {
            winParams.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        }
        win.setAttributes(winParams);
    }
    private void setRotationAnimation(int rotationAnimation) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        winParams.rotationAnimation = rotationAnimation;
        win.setAttributes(winParams);
    }
}