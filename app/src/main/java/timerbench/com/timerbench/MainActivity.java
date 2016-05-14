package timerbench.com.timerbench;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
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




@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity {
    Button btnSportTimer, btnStopwatch, btnReminder;
    SportTimer sportTimer = new SportTimer();
    Stopwatch stopwatch = new Stopwatch();
    Reminder reminder = new Reminder();


    Typeface tf;
    FragmentManager fTrans = getSupportFragmentManager();
    private Camera mCamera;
    private Camera.Parameters mParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(" Sport&Fighting Timer");
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("Sport&Fighting Timer");
        fTrans.beginTransaction()
                .replace(R.id.fragreplace, sportTimer).commit();

        btnSportTimer = (Button) findViewById(R.id.btnSportTimer);
        btnStopwatch = (Button) findViewById(R.id.btnStopwatch);
        btnReminder = (Button) findViewById(R.id.btnReminder);
    }
    public void onClick(View view) {

        switch (view.getId()){
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
//                setTitle("Alarm and Reminder");
//                fTrans.beginTransaction()
//                        .replace(R.id.fragreplace, reminder).commit();

//              Intent intent =;
//                startActivity( new Intent(this, AlyarnActivity.class));

               startActivity(new Intent(this, Ayarm_Activity.class));

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
}
