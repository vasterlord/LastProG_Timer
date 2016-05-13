package timerbench.com.timerbench;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageButton;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.app.Fragment;
import android.app.FragmentHostCallback;
import timerbench.com.timerbench.R;
import timerbench.com.timerbench.Stopwatch;
import timerbench.com.timerbench.SportTimer;
import timerbench.com.timerbench.Dev;
import timerbench.com.timerbench.Reminder;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)//implements NavigationView.OnNavigationItemSelectedListener
@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity {
    Button btnSportTimer, btnStopwatch, btnReminder;
    SportTimer sportTimer = new SportTimer();
    Stopwatch stopwatch = new Stopwatch();
    Reminder reminder = new Reminder();
    Dev dev;
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
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
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
                break;
            case R.id.btnStopwatch:
                setTitle("Stopwatch");
                fTrans.beginTransaction()
                        .replace(R.id.fragreplace, stopwatch).commit();
                break;
            case R.id.btnReminder:
                setTitle("Alarm and Reminder");
                fTrans.beginTransaction()
                        .replace(R.id.fragreplace, reminder).commit();
                break;
            default:
                break;
        }
    }
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        if (id == R.id.nav_dev) {
//            fTrans = getSupportFragmentManager();
//            dev = new Dev();
//            fTrans.beginTransaction()
//                    .replace(R.id.fragreplace, dev).commit();
//        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
}
