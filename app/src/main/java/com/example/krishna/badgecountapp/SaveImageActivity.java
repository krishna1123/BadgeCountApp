package com.example.krishna.badgecountapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by krishna on 13/5/15.
 */
public class SaveImageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_image_activity);

        final ImageView ivSaved= (ImageView) findViewById(R.id.iv_saved_image);
        final Button btnSave= (Button) findViewById(R.id.btn_save);

        btnSave.setDrawingCacheEnabled(true);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap=btnSave.getDrawingCache();
                ivSaved.setImageBitmap(bitmap);

                try {
                    File savedFile=saveBitmap(bitmap);
                    scanFile(savedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void scanFile(File file){
        Intent intent =
                new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }

    public File saveBitmap(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

//you can create a new file name "test.jpg" in sdcard folder.
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "test_" +System.currentTimeMillis()+".png");
        f.createNewFile();
//write the bytes in file
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());

// remember close de FileOutput
        fo.close();
        return f;
    }
}
