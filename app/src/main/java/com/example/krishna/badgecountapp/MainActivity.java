package com.example.krishna.badgecountapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.lang.reflect.Method;
import java.util.List;

 //Template for logcat -->checked first three
        // android.util.Log.d("$className$", "$MethodName$ (Line:$Line$) :"+$message$);

        // Template for showToast // "this"  //  "\"message\""
        // android.widget.Toast.makeText($context$, $message$, Toast.LENGTH_SHORT).show();$END$


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setBadge(this, 5);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public static void setBadge(Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    public static String getLauncherClassName(Context context) {

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }

    public void endPhoneCall2(TelephonyManager telephony){
        Log.d("PhoneCallReceiver", "endPhoneCall2 (Line:99) :");
        try {
            // get get telephony interface method
            Method _getITelephonyMethod = TelephonyManager.class
                    .getDeclaredMethod("getITelephony", (Class[]) null);
            // set get telephony interface method access permission
            _getITelephonyMethod.setAccessible(true);
            // get telephony interface object
            Object _ITelephonyObject = _getITelephonyMethod.invoke(
                    telephony, (Object[]) null);
            // get end call method
            Method _endCallMethod = _ITelephonyObject.getClass()
                    .getDeclaredMethod("endCall", (Class[]) null);
            // set end call method access permission
            _endCallMethod.setAccessible(true);
            // reject the incoming call
            _endCallMethod.invoke(_ITelephonyObject, (Object[]) null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void endPhoneCall(TelephonyManager telephony){
        Log.d("PhoneCallReceiver", "endPhoneCall2 (Line:99) :");
        try {
            Method iTelephonyMethod = TelephonyManager.class.getDeclaredMethod("getITelephony", (Class[]) null);
            iTelephonyMethod.setAccessible(true);
            Object iTelephonyObj = iTelephonyMethod.invoke(telephony, (Object[]) null);
            Method endCallMethod = iTelephonyObj.getClass().getDeclaredMethod("endCall", (Class[]) null);
            endCallMethod.setAccessible(true);
            endCallMethod.invoke(iTelephonyObj, (Object[]) null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
    }

    //    /**
//     * It ends the phone call.
//     *
//     * @param telephony
//     */
//    private void endPhoneCall3(TelephonyManager telephony) {
//        Log.d("PhoneCallReceiver", "endPhoneCall (Line:84) :");
//        try {
//            Class c = Class.forName(telephony.getClass().getName());
//            Method m = c.getDeclaredMethod("getITelephony");
//            m.setAccessible(true);
//            telephonyService = (ITelephony) m.invoke(telephony);
//            telephonyService.endCall();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


//    Window window = getWindow();
//    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//    window.setStatusBarColor(getResources().getColor(R.color.text_blue_color));
}
