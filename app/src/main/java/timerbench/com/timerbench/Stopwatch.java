package timerbench.com.timerbench;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;


public class Stopwatch extends Fragment {
    static String PREFS_NAME = "StopWatchState";
    private boolean backPressed;
    private Handler customHandler = new Handler();
    private Button resetLapButton;
    private boolean startEnabled = true;
    private Button startPauseButton;
    private long startTime = 0;
    public long timeInMilliseconds = 0;
    public long timeSwapBuff = 0;
    TextView timerValue;
    TextView timerValue2;
    public Runnable updateTimerThread;
    public long updatedTime = 0;
    private EditText eTLaps;
    private ScrollView mSVLaps;
    private int lap = 1;
    MediaPlayer mPlayerstart;
    Typeface tf;
    public Stopwatch(){
        this.updateTimerThread = new Runnable() {
            public void run() {
                Stopwatch.this.updateDigits();
                Stopwatch.this.customHandler.postDelayed((Runnable) this, 0);
            }
        };
        this.backPressed = false;
    }

    static /* synthetic */ void access$2(Stopwatch stopwatch, boolean bl) {
        stopwatch.startEnabled = bl;
    }

    static /* synthetic */ void access$5(Stopwatch stopwatch, long l) {
        stopwatch.startTime = l;
    }

    protected void initialState() {
        this.customHandler.removeCallbacks(this.updateTimerThread);
        this.startPauseButton.setText((CharSequence) "Start");
        this.startEnabled = true;
        this.timerValue.setText((CharSequence) "0:00");
        this.timeInMilliseconds = 0;
        this.updatedTime = 0;
        this.timeSwapBuff = 0;
        this.startTime = 0;
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stopwatch, null);
        timerValue = (TextView) v.findViewById(R.id.textView);
        timerValue2 = (TextView) v.findViewById(R.id.textView2);
        tf = Typeface.createFromAsset(getContext().getAssets(),"ds-digital.ttf");
        timerValue.setTypeface(tf);
        timerValue2.setTypeface(tf);
        startPauseButton = (Button) v.findViewById(R.id.buttonS);
        eTLaps = (EditText) v.findViewById(R.id.et_laps);
        mSVLaps = (ScrollView) v.findViewById(R.id.sv_laps);
        Context con = getContext();
        mPlayerstart = MediaPlayer.create(con, R.raw.ok);
        this.startPauseButton.setOnClickListener(new OnClickListener() { /* * Enabled aggressive block sorting */
            public void onClick(View view) {
                Stopwatch stopwatch = Stopwatch.this;
                boolean bl = !Stopwatch.this.startEnabled;
                Stopwatch.access$2(stopwatch, bl);
                if (Stopwatch.this.startEnabled) {
                    Stopwatch.this.startPauseButton.setText((CharSequence) "Start");
                    Stopwatch stopwatch1 = Stopwatch.this;
                    stopwatch1.timeSwapBuff += Stopwatch.this.timeInMilliseconds;
                    Stopwatch.this.customHandler.removeCallbacks(Stopwatch.this.updateTimerThread);
                    resetLapButton.setText("Reset");
                    return;
                }
                Stopwatch.this.startPauseButton.setText((CharSequence) "Pause");
                Stopwatch.access$5(Stopwatch.this, SystemClock.uptimeMillis());
                Stopwatch.this.customHandler.postDelayed(Stopwatch.this.updateTimerThread, 0);
                resetLapButton.setText("Lap");
                Context context = getContext();
                Intent notificationIntent = new Intent();
                PendingIntent contentIntent = PendingIntent.getActivity(context,
                        0, notificationIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationManager nm = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                Resources res = context.getResources();
                Notification.Builder builder = new Notification.Builder(context);
                mPlayerstart.start();
                //  Uri ringURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                long[] vibrate = new long[] { 1000, 1000, 1000, 1000 };
                builder.setContentIntent(contentIntent)
                        .setTicker(" OKEY LETS GO!!! ")
                        .setSmallIcon(R.drawable.clocksicon)
                        .setWhen(System.currentTimeMillis())
                        //  .setSound(ringURI)
                        .setVibrate(vibrate)
                        .setContentTitle(" Notification ")
                        .setContentText(" Stopwatch was started!");
                Notification notification = builder.build();
                notification.defaults = Notification.DEFAULT_SOUND |
                        Notification.DEFAULT_VIBRATE;
                notification.flags = notification.flags | Notification.FLAG_SHOW_LIGHTS;
                notification.flags = notification.flags | Notification.FLAG_INSISTENT;
                Notification n = builder.getNotification();
                nm.notify(1, n);
            }
        });
        this.resetLapButton = (Button) v.findViewById(R.id.buttonR);
        this.resetLapButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (resetLapButton.getText().toString().equals("Reset")) {
                    timerValue2.setText("00");
                    Stopwatch.this.initialState();
                    lap = 1;
                    eTLaps.setText("");
                }
                else{
                    if(resetLapButton.getText().toString().equals("Lap")) {
                        eTLaps.append("#" + String.valueOf(lap) +
                                "     " + String.valueOf(timerValue.getText()) + ":" + String.valueOf(timerValue2.getText()) + "\n");

                        mSVLaps.post(new Runnable() {
                            @Override
                            public void run() {
                                mSVLaps.smoothScrollTo(0, eTLaps.getBottom());
                            }
                        });

                        lap++;
                    }
                }

            }
        });
        return v;
    }

    public void onPause() {
        super.onPause();
    }

    public void updateDigits() {
        this.timeInMilliseconds = SystemClock.uptimeMillis() - this.startTime;
        this.updatedTime = this.timeSwapBuff + this.timeInMilliseconds;
        int n = (int) (this.updatedTime / 1000);
        int n2 = n / 60;
        int n3 = n % 60;
        int n4 = (int) (this.updatedTime % 1000) / 10;
        TextView textView = this.timerValue;
        StringBuilder stringBuilder = new StringBuilder().append(n2).append(":");
        Object[] arrobject = new Object[]{n3};
        textView.setText((CharSequence) stringBuilder.append(String.format((String) "%02d", (Object[]) arrobject)).toString());
        TextView textView2 = this.timerValue2;
        StringBuilder stringBuilder3 = new StringBuilder();
        Object[] arrobject3 = new Object[]{n4};
        textView2.setText((CharSequence) stringBuilder3.append(String.format((String) "%02d", (Object[]) arrobject3)).toString());

    }
}