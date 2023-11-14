package com.app.beyondlottotv.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.app.beyondlottotv.Activities.ChooseScreenActivity;

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent activityIntent = new Intent(context, ChooseScreenActivity.class);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(activityIntent);
        }
    }
}





