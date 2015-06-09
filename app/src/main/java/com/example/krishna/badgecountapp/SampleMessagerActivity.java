package com.example.krishna.badgecountapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class SampleMessagerActivity extends ActionBarActivity implements ServiceConnection{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_messager);

        Intent binderIntent=new Intent(this, MyMessangerService.class);
        bindService(binderIntent, this, BIND_AUTO_CREATE);

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain(null, MyMessangerService.HELLO_MSG, 0, 0);
                Bundle bundle = new Bundle();
                bundle.putString("data", "Pavan");

                MyBean bean = new MyBean();
                bean.setName("Krishna");
                bundle.putParcelable("bean", bean);

                message.setData(bundle);

                try {
                    messenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    boolean isBound = false;
    Messenger messenger;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        isBound=true;
        messenger=new Messenger(service);

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

        messenger=null;
        isBound=false;
    }
}
