package com.example.lenovo.myfinalproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    Context context;
    private static final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

       this.context = context;
        String action = intent.getAction();
        if (action.equals(BOOT_ACTION)){
            Intent intent1 = new Intent(context,MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}
