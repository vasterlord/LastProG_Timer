package timerbench.com.timerbench.resivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import timerbench.com.timerbench.model.UserAlyarm;

/**
        * Created by Ivan on 14.05.2016.
        */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        List<Intent> intents = new ArrayList<>();
//        Log.d("TAG", "ALERT!!");
//
//        intents.add(new Intent(context, RingAlarm.class));
//        String message = intent.getExtras().getString("message", "Alarm started");
//
//        intents.get(0).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intents.get(0).putExtra("message",message);
//        context.startActivity( intents.get(0));

        Log.d("TAG", "ALERT!!");
        Intent intent1 = new Intent(context, RingAlarm.class);
        String message = intent.getExtras().getString("message", "Alarm started");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("message",message);
        context.startActivity(intent1);
    }
}
