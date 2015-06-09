package com.example.krishna.badgecountapp;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by krishna on 12/5/15.
 */
public class SetWallpaperActivity extends Activity {
    final static private int[] mColors =
            {Color.BLUE, Color.GREEN, Color.RED, Color.LTGRAY, Color.MAGENTA, Color.CYAN,
                    Color.YELLOW, Color.WHITE};
    /**
     * Initialization of the Activity after it is first created. Must at least
     * call {@link android.app.Activity#setContentView setContentView()} to
     * describe what is to be displayed in the screen.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
// Be sure to call the super class.
        super.onCreate(savedInstanceState);
// See res/layout/wallpaper_2.xml for this
// view layout definition, which is being set here as
// the content of our screen.
        setContentView(R.layout.activity_wallpaper);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        final ImageView imageView = (ImageView) findViewById(R.id.iv_wall_1);
        imageView.setDrawingCacheEnabled(true);
        imageView.setImageDrawable(wallpaperDrawable);
        Button randomize = (Button) findViewById(R.id.btn_1);
        randomize.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int mColor = (int) Math.floor(Math.random() * mColors.length);
                wallpaperDrawable.setColorFilter(mColors[mColor], PorterDuff.Mode.MULTIPLY);
                imageView.setImageDrawable(wallpaperDrawable);
                imageView.invalidate();
            }
        });
        Button setWallpaper = (Button) findViewById(R.id.btn_2);
        setWallpaper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    wallpaperManager.setBitmap(imageView.getDrawingCache());
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
