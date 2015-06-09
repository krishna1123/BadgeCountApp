package com.example.krishna.badgecountapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;

public class MyService extends Service {

   // private MyBinder binder=new MyBinder();

    public MyService() {
    }

    /*
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }*/

    /*public void sendMessage(String msg){
        Toast.makeText(this, "Hello "+msg, Toast.LENGTH_SHORT).show();
    }*/

  /*  class MyBinder extends Binder{

        MyService getInstance(){
            return MyService.this;
        }
    }*/

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Srv", "OnBind");
        final ResultReceiver rec = (ResultReceiver) intent.getParcelableExtra("rec");

        return new IMyAidlInterface.Stub() {

            public MyBean sendMsg(MyBean msg){
                MyBean bean=new MyBean();
                bean.setName("Hello "+msg.getName());
                System.out.println("AIDLOUTPUT:"+bean.getName());
               return bean;
            }

           /* public void setListener(MyListener listener){

                System.out.println("HAI");
                //System.out.println("Listener:"+listener.getData());

            }*/
        };
    }
}
