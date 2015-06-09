package com.example.krishna.badgecountapp;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.widget.Toast;

public class MyMessangerService extends Service {


    static final int HELLO_MSG=1;

    public MyMessangerService() {
    }

    private Messenger myMessanger=new Messenger(new MyIncomingHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return myMessanger.getBinder();
    }

    private class MyIncomingHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HELLO_MSG:
                    Bundle bundle = msg.getData();
                    bundle.setClassLoader(this.getClass().getClassLoader());

                    String data=bundle.getString("data");

                    MyBean bean=bundle.getParcelable("bean");

                    System.out.println("DATA:"+data);
                    Toast.makeText(getApplicationContext(), "Hai "+data+" "+bean.getName(), Toast.LENGTH_SHORT).show();
                    break;

                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }
}
