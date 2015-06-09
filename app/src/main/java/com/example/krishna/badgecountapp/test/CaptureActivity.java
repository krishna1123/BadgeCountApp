package com.example.krishna.badgecountapp.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.krishna.badgecountapp.R;

import java.io.File;

/**
 * Created by krishna on 29/5/15.
 */
public class CaptureActivity extends Activity {

    private String photoFileName="abc.png";
    private Uri imageUrl;
    private ImageView mImageView;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture_layout);

        Button capture= (Button) findViewById(R.id.btn_capture);
        mImageView = (ImageView) findViewById(R.id.iv_capture);

        capture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName));
                    startActivityForResult(intent, 1001);
                }

            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                imageUrl = getPhotoFileUri(photoFileName);

                mBitmap = BitmapFactory.decodeFile(imageUrl.getPath());
                Log.d("TestTag", "ImageUrl:" + imageUrl.getPath());
                galleryAddPic() ;
                mImageView.setImageBitmap(mBitmap);
            } else {
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void galleryAddPic() {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUrl);
        this.sendBroadcast(mediaScanIntent);
    }
    public Uri getPhotoFileUri(String fileName) {

    /*File mediaStorageDir = new File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Images");*/
        File mediaStorageDir=null;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Images");
        else
            mediaStorageDir=getApplicationContext().getCacheDir();
        if(!mediaStorageDir.exists()&& !mediaStorageDir.mkdirs())
            mediaStorageDir.mkdirs();
        //  return mediaStorageDir;
    /*if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){

    }*/
        return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
    }
}
