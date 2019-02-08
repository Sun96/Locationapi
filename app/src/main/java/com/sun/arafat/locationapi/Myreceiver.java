package com.sun.arafat.locationapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by Arafat on 7/24/2016.
 */
public class Myreceiver extends BroadcastReceiver {
int i;
    private long lastPressedTime;
    public static boolean wasScreenOn = true;
    DatabaseHepler mydb;
    PersonDatabase psdb;


    @Override

    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

            wasScreenOn = false;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            i++;
            if (i==1)
            Toast.makeText(context.getApplicationContext(), "Press "+i+"Time nead 1 more time withine 3 sec", Toast.LENGTH_SHORT).show();
            if (i==2 && SystemClock.elapsedRealtime() - lastPressedTime < 3000){
                mydb = new DatabaseHepler(context);
                final Cursor res = mydb.getAllData();
                //...........................................
                psdb= new PersonDatabase(context);
                final Cursor res2 = psdb.getAllData();
            while (res2.moveToNext()) {
                    final String num = res2.getString(2);
                final String number=num;

                while (res.moveToNext()) {
                    final String latitude = res.getString(1);
                    String longitude = res.getString(2);
                    final String message = "I am in denger. plz help me. Her is my location  "+"https://www.google.com.bd/maps/@" + latitude + "," + longitude + ",16z?hl=en";

                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(number, null, message, null, null);
                        Toast.makeText(context.getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(1000);
                    } catch (Exception e) {
                        Toast.makeText(context.getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
                i=0;
            }
            lastPressedTime = SystemClock.elapsedRealtime();
            if (i==0) {i=0;}
            if(i>=2){i=0;}

            wasScreenOn = true;



        }

    }


}
