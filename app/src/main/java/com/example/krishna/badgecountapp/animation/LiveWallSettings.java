package com.example.krishna.badgecountapp.animation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import com.example.krishna.badgecountapp.R;

public class LiveWallSettings extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_wallpaper);

        final EditText etName = (EditText) findViewById(R.id.et_name);
        final EditText etSize = (EditText) findViewById(R.id.et_size);
        final EditText etColor = (EditText) findViewById(R.id.et_color);

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String siz = etSize.getText().toString();
                int size=35;

                if (siz.trim().length() == 0) {
                    size = 35;
                    etSize.setText("" + 35);
                } else {
                    size = Integer.parseInt(siz);
                }

                SharedPreferences pref=getSharedPreferences("LiveWall", MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("name", name);
                editor.putInt("size", size);
                editor.commit();
            }
        });
    }
}
