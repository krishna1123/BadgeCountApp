package com.example.krishna.badgecountapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by krishna on 12/5/15.
 */
public class ScreenRecordFragment extends Activity {

    private EditText mWidthEditText;
    private EditText mHeightEditText;
    private EditText mBitrateEditText;
    private EditText mTimeEditText;
    private Button mRecordButton;

    public ScreenRecordFragment() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_recording_fragment);

        mRecordButton = (Button) findViewById(R.id.btn_record);
        mRecordButton.setOnClickListener(RecordOnClickListener);

        mWidthEditText = (EditText) findViewById(R.id.et_width);
        mHeightEditText = (EditText) findViewById(R.id.et_height);
        mBitrateEditText = (EditText) findViewById(R.id.et_bitrate);
        mBitrateEditText.addTextChangedListener(BitrateTextWatcher);
        mTimeEditText = (EditText) findViewById(R.id.et_time);
        mTimeEditText.addTextChangedListener(TimeTextWatcher);
    }

    private TextWatcher BitrateTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i,
                                      int i2, int i3) {
            // Not used.
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2,
                                  int i3) {
            if (TextUtils.isEmpty(charSequence)) {
                return;
            }

            int value = Integer.valueOf(charSequence.toString());
            if (value > 50 || value == 0) {
                mBitrateEditText.setError("Error in Bitrate (0 - 50)");
                return;
            }

            mTimeEditText.setError(null);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // Not used.
        }
    };

    private TextWatcher TimeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i,
                                      int i2, int i3) {
            // Not used.
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2,
                                  int i3) {
            if (TextUtils.isEmpty(charSequence)) {
                return;
            }

            int value = Integer.valueOf(charSequence.toString());
            if (value > 180 || value == 0) {
                mTimeEditText.setError("Error in time (0 - 180)");
                return;
            }
            mTimeEditText.setError(null);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // Not used.
        }
    };

    private View.OnClickListener RecordOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!TextUtils.isEmpty(mTimeEditText.getError())
                    || !TextUtils.isEmpty(mBitrateEditText.getError())) {
                Log.d("Recording", "Invalid values");
                Toast.makeText(getApplicationContext(),
                        "Invalid values",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            boolean widthSet = !TextUtils.isEmpty(mWidthEditText.getText());
            boolean heightSet = !TextUtils.isEmpty(mHeightEditText
                    .getText());
            if ((!widthSet && heightSet) || (widthSet && !heightSet)) {
                Toast.makeText(getApplicationContext(),
                        "Invalid width set or otherthings",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            boolean bitrateSet = !TextUtils.isEmpty(mBitrateEditText
                    .getText());
            boolean timeSet = !TextUtils.isEmpty(mTimeEditText.getText());

            StringBuilder stringBuilder = new StringBuilder(
                    "/system/bin/screenrecord");
            if (widthSet) {
                stringBuilder.append(" --size ")
                        .append(mWidthEditText.getText()).append("x")
                        .append(mHeightEditText.getText());
            }
            if (bitrateSet) {
                stringBuilder.append(" --bit-rate ").append(
                        mBitrateEditText.getText());
            }
            if (timeSet) {
                stringBuilder.append(" --time-limit ").append(
                        mTimeEditText.getText());
            }

            // TODO User definable location.
            stringBuilder
                    .append(" ")
                    .append(Environment.getExternalStorageDirectory()
                            .toString()).append("/rec" + System.currentTimeMillis() + ".mp4");
            Log.d("TAG", "comamnd: " + stringBuilder.toString());

            Toast.makeText(getApplicationContext(),
                    "Command",
                    Toast.LENGTH_SHORT).show();

            try {
                new SuTask(stringBuilder.toString().getBytes("ASCII"))
                        .execute();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Exception:" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    private class SuTask extends AsyncTask<Boolean, Void, Boolean> {
        private final byte[] mCommand;

        public SuTask(byte[] command) {
            super();
            this.mCommand = command;
        }

        @Override
        protected Boolean doInBackground(Boolean... booleans) {
            try {
                Process sh = Runtime.getRuntime().exec("su", null, null);
                OutputStream outputStream = sh.getOutputStream();
                outputStream.write(mCommand);
                outputStream.flush();
                outputStream.close();

                final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(RUNNING_NOTIFICATION_ID,
                        createRunningNotification(getApplicationContext()));

                sh.waitFor();
                return true;

            } catch (InterruptedException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Error in start recording",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Recording", "Error in start recording");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Error in start recording",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            return false;
        }

        private static final int RUNNING_NOTIFICATION_ID = 10;
        private static final int FINISHED_NOTIFICATION_ID = 11;

        @Override
        protected void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if (bool) {
                final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(RUNNING_NOTIFICATION_ID);

                File file = new File(Environment
                        .getExternalStorageDirectory().toString()
                        + "/rec" + System.currentTimeMillis() + ".mp4");
                notificationManager.notify(FINISHED_NOTIFICATION_ID,
                        createFinishedNotification(getApplicationContext(), file));
            }
        }

        private Notification createRunningNotification(Context context) {
            Notification.Builder mBuilder = new Notification.Builder(
                    context)
                    .setSmallIcon(android.R.drawable.stat_notify_sdcard)
                    .setContentTitle(
                            context.getResources().getString(
                                    R.string.app_name))
                    .setContentText("Recording Running")
                    .setTicker("Recording Running")
                    .setPriority(Integer.MAX_VALUE).setOngoing(true);

            return mBuilder.build();
        }

        private Notification createFinishedNotification(Context context,
                                                        File file) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "video/mp4");

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder mBuilder = new Notification.Builder(
                    context)
                    .setSmallIcon(android.R.drawable.stat_notify_sdcard)
                    .setContentTitle(
                            context.getResources().getString(
                                    R.string.app_name))
                    .setContentText("Recording Finished")
                    .setTicker("Recording Finished")
                    .setContentIntent(pendingIntent).setOngoing(false)
                    .setAutoCancel(true);

            return mBuilder.build();
        }
    }
}
