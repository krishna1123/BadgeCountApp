package com.example.krishna.badgecountapp.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishna.badgecountapp.R;

import java.io.FileNotFoundException;

public class ImageActivity extends Activity {

        Button btnLoadImage1, btnLoadImage2;
        TextView textSource1, textSource2;
        Button btnProcessing;
        ImageView imageResult;
        Spinner spinnerMode;

        final int RQS_IMAGE1 = 1;
        final int RQS_IMAGE2 = 2;

        Uri source1, source2;

        String[] arrayModeName = {
                "ADD",
                "CLEAR",
                "DARKEN",
                "DST",
                "DST_ATOP",
                "DST_IN",
                "DST_OUT",
                "DST_OVER",
                "LIGHTEN",
                "MULTIPLY",
                "OVERLAY",
                "SCREEN",
                "SRC",
                "SRC_ATOP",
                "SRC_IN",
                "SRC_OUT",
                "SRC_OVER",
                "XOR" };

        /*
         * To use Mode.ADD and Mode.OVERLAY, android:minSdkVersion
         * have to be set "11" or higher.
         */
        PorterDuff.Mode[] arrayMode = {
                Mode.ADD,
                Mode.CLEAR,
                Mode.DARKEN,
                Mode.DST,
                Mode.DST_ATOP,
                Mode.DST_IN,
                Mode.DST_OUT,
                Mode.DST_OVER,
                Mode.LIGHTEN,
                Mode.MULTIPLY,
                Mode.OVERLAY,
                Mode.SCREEN,
                Mode.SRC,
                Mode.SRC_ATOP,
                Mode.SRC_IN,
                Mode.SRC_OUT,
                Mode.SRC_OVER,
                Mode.XOR };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_image);
            btnLoadImage1 = (Button)findViewById(R.id.loadimage1);
            btnLoadImage2 = (Button)findViewById(R.id.loadimage2);
            textSource1 = (TextView)findViewById(R.id.sourceuri1);
            textSource2 = (TextView)findViewById(R.id.sourceuri2);
            btnProcessing = (Button)findViewById(R.id.processing);
            imageResult = (ImageView)findViewById(R.id.result);

            spinnerMode = (Spinner)findViewById(R.id.mode);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ImageActivity.this,
                    android.R.layout.simple_spinner_item, arrayModeName);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMode.setAdapter(adapter);

            btnLoadImage1.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RQS_IMAGE1);
                }});

            btnLoadImage2.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RQS_IMAGE2);
                }});

            btnProcessing.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    if(source1 != null && source2 != null){
                        Bitmap processedBitmap = ProcessingBitmap();
                        if(processedBitmap != null){
                            imageResult.setImageBitmap(processedBitmap);
                            Toast.makeText(getApplicationContext(),
                                    "Done",
                                    Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Something wrong in processing!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Select both image!",
                                Toast.LENGTH_LONG).show();
                    }

                }});
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_OK){
                switch (requestCode){
                    case RQS_IMAGE1:
                        source1 = data.getData();
                        textSource1.setText(source1.toString());
                        break;
                    case RQS_IMAGE2:
                        source2 = data.getData();
                        textSource2.setText(source2.toString());
                        break;
                }
            }
        }

        private Bitmap ProcessingBitmap(){
            Bitmap bm1 = null;
            Bitmap bm2 =  null;
            Bitmap newBitmap = null;

            try {
                bm1 = BitmapFactory.decodeStream(
                        getContentResolver().openInputStream(source1));
                bm2 = BitmapFactory.decodeStream(
                        getContentResolver().openInputStream(source2));

                int w;
                if(bm1.getWidth() >= bm2.getWidth()){
                    w = bm1.getWidth();
                }else{
                    w = bm2.getWidth();
                }

                int h;
                if(bm1.getHeight() >= bm2.getHeight()){
                    h = bm1.getHeight();
                }else{
                    h = bm2.getHeight();
                }

                Bitmap.Config config = bm1.getConfig();
                if(config == null){
                    config = Bitmap.Config.ARGB_8888;
                }

                newBitmap = Bitmap.createBitmap(w, h, config);
                Canvas newCanvas = new Canvas(newBitmap);

                newCanvas.drawBitmap(bm1, 0, 0, null);

                Paint paint = new Paint();

                int selectedPos = spinnerMode.getSelectedItemPosition();
                PorterDuff.Mode selectedMode = arrayMode[selectedPos];

                paint.setXfermode(new PorterDuffXfermode(selectedMode));
                newCanvas.drawBitmap(bm2, 0, 0, paint);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return newBitmap;
        }

/*
    private Bitmap getRoundedCornerBitmap(Bitmap bitmap){
        Bitmap output=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(output);
        final Paint paint=new Paint();
        final Rect rect=new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        final RectF rectF=new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0,0,0,0);
        paint.setColor(0xFFFFFFFF);
        float roundPixels=bitmap.getWidth()<bitmap.getHeight()?bitmap.getWidth():bitmap.getHeight();
        canvas.drawRoundRect(rectF,roundPixels,roundPixels,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,rect,rect,paint);
        return output;
    }*/

    }
