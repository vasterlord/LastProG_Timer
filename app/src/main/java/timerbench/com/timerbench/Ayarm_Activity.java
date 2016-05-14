package timerbench.com.timerbench;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import timerbench.com.timerbench.adapters.AlyarmAdapter;
import timerbench.com.timerbench.model.UserAlyarm;
import timerbench.com.timerbench.resivers.AlarmReceiver;

/**
 * Created by Ivan on 14.05.2016.
 */
public class Ayarm_Activity extends AppCompatActivity {
    private static final int REQUEST_CODE = 23;
    private static final int REQUEST_CODE_ALERT = 24;
    private TextView timeTextView;
    private ListView listView;
    private AlyarmAdapter alarmAdapter;
    private ArrayList<UserAlyarm> userAlarmArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_alyarm);
        timeTextView = (TextView)findViewById(R.id.textViewTime);
        listView = (ListView)findViewById(R.id.listView);

        showCurrentDate();
        userAlarmArrayList = new ArrayList<>();
        alarmAdapter = new AlyarmAdapter(this, userAlarmArrayList);
        listView.setAdapter(alarmAdapter);
    }

    public void onChooserClick(View v) {
        Intent intent = new Intent(this, TimeActivity.class);
//        startActivity(intent);
       startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE) {

            if (data != null && data.getExtras() != null) {
                Bundle bundle = data.getExtras();
                String message = bundle.getString("message", "Alarm started");
                Calendar calendar = (Calendar) bundle.get("calendar");

                if (calendar != null) {
                    startAlert(calendar, message);
                    UserAlyarm userAlarm = new UserAlyarm();
                    userAlarm.setMessage(message);
                    userAlarm.setTime(DateFormat.getDateTimeInstance().format(calendar.getTime()));
                    userAlarmArrayList.add(0, userAlarm);
                    alarmAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    public void startAlert(Calendar calendar, String message) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), REQUEST_CODE_ALERT, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +
                (calendar.getTimeInMillis() - System.currentTimeMillis()), pendingIntent);
        Toast.makeText(this, "Alarm set", Toast.LENGTH_LONG).show();
    }

    private void showCurrentDate() {
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeTextView.setText("Current time: " + DateFormat.getDateTimeInstance().format(new Date()));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    System.out.println(e.toString());
                }
            }
        };
        t.start();
    }
}
