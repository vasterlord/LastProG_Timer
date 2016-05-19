package timerbench.com.timerbench;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity {
    Button btnSportTimer, btnStopwatch, btnReminder, buttonDTime;
    SportTimer sportTimer = new SportTimer();
    Stopwatch stopwatch = new Stopwatch();
    SharedPreferences sharedPreferencesReset;

    Typeface tf;
    FragmentManager fTrans = getSupportFragmentManager();
    private Camera mCamera;
    private Camera.Parameters mParams;


    //    ---------------------------------------------------------------------------------------
//    ---------------------------------------------------------------------------------------
    private static final int REQUEST_CODE = 23;
    private static final int REQUEST_CODE_ALERT = 24;
    private TextView timeTextView;
    private ListView listView;
    private AlyarmAdapter alarmAdapter;
    private ArrayList <UserAlyarm> userAlarmArrayList;
    //    ---------------------------------------------------------------------------------------
//    --------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //    ---------------------------------------------------------------------------------------
//    ---------------------------------------------------------------------------------------
        timeTextView = (TextView) findViewById(R.id.textViewTime);
        listView = (ListView) findViewById(R.id.listView);

        showCurrentDate();
        userAlarmArrayList = new ArrayList<>();
        alarmAdapter = new AlyarmAdapter(this, userAlarmArrayList);
        listView.setAdapter(alarmAdapter);
// ---------------------------------------------------------------------------------------
//    ---------------------------------------------------------------------------------------


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(" Sport&Fighting Timer");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("Sport&Fighting Timer");
        fTrans.beginTransaction()
                .replace(R.id.fragreplace, sportTimer).commit();

        btnSportTimer = (Button) findViewById(R.id.btnSportTimer);
        btnStopwatch = (Button) findViewById(R.id.btnStopwatch);
        btnReminder = (Button) findViewById(R.id.btnReminder);
        buttonDTime = (Button) findViewById(R.id.buttonChooser);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnSportTimer:
                setTitle("Sport&Fighting Timer");
                fTrans.beginTransaction()
                        .replace(R.id.fragreplace, sportTimer).commit();
                btnSportTimer.setTextSize(20);
                btnReminder.setTextSize(15);
                btnStopwatch.setTextSize(15);

                break;
            case R.id.btnStopwatch:
                setTitle("Stopwatch");
                fTrans.beginTransaction()
                        .replace(R.id.fragreplace, stopwatch).commit();

                btnSportTimer.setTextSize(15);
                btnReminder.setTextSize(15);
                btnStopwatch.setTextSize(20);
                break;
            case R.id.btnReminder:
                setTitle("Alarm and Reminder");
                fTrans.beginTransaction()
                        .remove(sportTimer).commit();
                fTrans.beginTransaction()
                        .remove(stopwatch).commit();

                btnSportTimer.setTextSize(15);
                btnReminder.setTextSize(20);
                btnStopwatch.setTextSize(15);
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    //------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------
    public void onChooserClick(View v) {
          Intent intent = new Intent(this, TimeDate.class);
          startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {

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
