package com.example.krishna.badgecountapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by krishna on 11/5/15.
 */
public class SampleAidlActivity extends Activity implements ServiceConnection {

    private Button btnSend;
    private MyService myService;
    private IMyAidlInterface myAddService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

//        Intent intent=new Intent(this, MyService.class);
//        startService(intent);
        Intent binderIntent = new Intent(SampleAidlActivity.this, MyService.class);
        bindService(binderIntent, SampleAidlActivity.this, BIND_AUTO_CREATE);

        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // myService.sendMessage("Krishna");
                try {
                    MyBean bean = new MyBean();
                    bean.setName("Krishna");
                    MyBean msg = myAddService.sendMsg(bean);

                   /* myAddService.setListener(new MyListener.Stub(){
                        public void setData(String data) throws RemoteException{
                            System.out.println("Data:"+data);
                        }
                        public int getData()  throws RemoteException{
                            return 7777;
                        }
                    });*/

                    Toast.makeText(SampleAidlActivity.this, "" + msg.getName(), Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }// 1410403125
            }
        });



    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        /*MyService.MyBinder binder = (MyService.MyBinder) service;
        myService = binder.getInstance();*/
        myAddService = IMyAidlInterface.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
