// IMyAidlInterface.aidl
package com.example.krishna.badgecountapp;

import com.example.krishna.badgecountapp.MyBean;
//import com.example.krishna.badgecountapp.MyListener;
// Declare any non-default types here with import statements

//interface MyListener {
//    void setData(String data);
//    int getData();
//}

interface IMyAidlInterface {

    //MyBean sendMsg(in MyBean msg);
    MyBean sendMsg(in MyBean inMsg);

   // void setListener(in MyListener listener);
}

