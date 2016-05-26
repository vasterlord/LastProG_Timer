package timerbench.com.timerbench;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SportTimer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SportTimer#newInstance} factory method to
 * create an instance of this fragment.
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class SportTimer extends Fragment {
    Button btnStart, btnStop, btnRounds, btnPrepare, btnWork, btnRest;
    EditText edRounds;
    TextView textViewTime, txtTittle;
    Typeface tf;
    LinearLayout pincers;
    String tm,timeout;
    int rounds = 1;
    int nrounds = 0;
    int end = 0;
    private static int [] parts = new int[3];
    private static int [] parts1 = new int[3];
    private static int [] parts2 = new int[3];
    private static long[] full = new long[3];
    long[] mfull = new long[3];
    private static int sHour;
    private static int sMinute;
    private static int sSecond;
    private static long time, work, prep, rest;
    private static long hours;
    private static long min;
    private static long sec;
    CounterClass timer,timer2,timer3, timer4;
    MediaPlayer mPlayerstart , mPlayerfinish;
    int setTimeOption = 1;
    int i = 0;
    String PREF_NAME = "pref";
    String PREF_NAME2 = "pref2";
    String PREF_NAME3 = "pref3";
    String PREF_NAME4 = "pref4";
    String PREF_NAME5 = "pref5";
    SharedPreferences sharedPreferences, sharedPreferences2, sharedPreferences3, sharedPreferences4, sharedPreferences5;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    NumberPicker npHours;
    NumberPicker npMinutes;
    NumberPicker npSeconds;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SportTimer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SportTimer.
     */
    // TODO: Rename and change types and number of parameters
    public static SportTimer newInstance(String param1, String param2) {
        SportTimer fragment = new SportTimer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sport_timer, null);
        Context con = getContext();
        mPlayerstart = MediaPlayer.create(con, R.raw.ok);
        mPlayerfinish = MediaPlayer.create(con, R.raw.finish);
        textViewTime = (TextView) v.findViewById(R.id.textViewTime);
        btnStart = (Button) v.findViewById(R.id.btnStart);
        btnStop = (Button) v.findViewById(R.id.btnStop);
        btnRest = (Button) v.findViewById(R.id. btnRest);
        btnPrepare = (Button) v.findViewById(R.id.btnPrepare);
        btnWork= (Button) v.findViewById(R.id.btnWork);
        btnRounds = (Button) v.findViewById(R.id.btnRounds);
        pincers = (LinearLayout) v.findViewById(R.id.pincers);
        txtTittle = (TextView) v.findViewById(R.id.txtTittle);
        edRounds = (EditText) v.findViewById(R.id.edRounds);
        tf = Typeface.createFromAsset(getContext().getAssets(),"ds-digital.ttf");
        textViewTime.setTypeface(tf);
        npHours = (NumberPicker) v.findViewById(R.id.npHours);
        npMinutes = (NumberPicker) v.findViewById(R.id.npMinute);
        npSeconds = (NumberPicker) v.findViewById(R.id.npSecond);
        npHours.setMaxValue(23);
        npHours.setMinValue(0);
        npHours.setWrapSelectorWheel(false);
        npMinutes.setMaxValue(59);
        npMinutes.setMinValue(0);
        npMinutes.setWrapSelectorWheel(false);
        npSeconds.setMaxValue(59);
        npSeconds.setMinValue(0);
        npSeconds.setWrapSelectorWheel(false);
        txtTittle.setText("");
        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SportTimer.full[0] = mfull[0];
                SportTimer.full[1] = mfull[1];
                SportTimer.full[2] = mfull[2];
                if (btnStart.getText().toString().equals("Start")) {
                    rounds = nrounds;
                    if ((setTimeOption == 1) && (rounds > 0)) {
                        timer = new CounterClass(SportTimer.full[0], 1000);
                        timer.start();
                        txtTittle.setText(" Preparing time: ");
                        setTimeOption = 2;
                    }
                    btnStart.setText("Stop");
                    btnStop.setText("Cancel");
                    pincers.setEnabled(false);
                    npHours.setEnabled(false);
                    npMinutes.setEnabled(false);
                    npSeconds.setEnabled(false);
                    textViewTime.setVisibility(View.VISIBLE);
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
                    long[] vibrate = new long[] {1000, 1000};
                    builder.setContentIntent(contentIntent)
                            .setTicker(" Lets go :) ")
                            .setSmallIcon(R.drawable.clocksicon)
                            .setWhen(System.currentTimeMillis())
                          //  .setSound(ringURI)
                            .setVibrate(vibrate)
                            .setContentTitle(" Notification ")
                            .setContentText(" Timer was started!");
                    Notification notification = builder.build();
                    notification.defaults = Notification.DEFAULT_SOUND |
                            Notification.DEFAULT_VIBRATE;
                    notification.flags = notification.flags | Notification.FLAG_SHOW_LIGHTS;
                    notification.flags = notification.flags | Notification.FLAG_INSISTENT;
                    Notification n = builder.getNotification();
                    nm.notify(1, n);
                }
                else {
                    timer.cancel();
                    btnStart.setText("Start");
                    btnStop.setText("Reset");
                    pincers.setEnabled(true);
                    npHours.setEnabled(true);
                    npMinutes.setEnabled(true);
                    npSeconds.setEnabled(true);
                    textViewTime.setVisibility(View.VISIBLE);
                    setTimeOption = 1;
                    end = 1;
                    Context context1 = getContext();
                    Intent notificationIntent1 = new Intent();
                    PendingIntent contentIntent1 = PendingIntent.getActivity(context1,
                            0, notificationIntent1,
                            PendingIntent.FLAG_CANCEL_CURRENT);
                    NotificationManager nm1 = (NotificationManager) context1
                            .getSystemService(Context.NOTIFICATION_SERVICE);
                    Resources res1 = context1.getResources();
                    Notification.Builder builder1 = new Notification.Builder(context1);
                    // Uri ringURI1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    mPlayerfinish.start();
                    long[] vibrate1 = new long[]{1000, 1000};
                    builder1.setContentIntent(contentIntent1)
                            .setTicker("You did it!?!")
                            .setSmallIcon(R.drawable.clocksicon)
                            .setWhen(System.currentTimeMillis())
                            //  .setSound(ringURI1)
                            .setVibrate(vibrate1)
                            .setContentTitle(" Notification ")
                            .setContentText(" Timer stopped ");
                    Notification notification1 = builder1.build();
                    notification1.defaults = Notification.DEFAULT_SOUND |
                            Notification.DEFAULT_VIBRATE;
                    notification1.flags = notification1.flags | Notification.FLAG_SHOW_LIGHTS;
                    notification1.flags = notification1.flags | Notification.FLAG_INSISTENT;
                    Notification n1 = builder1.getNotification();
                    nm1.notify(1, n1);
                }
            }

        });


        btnStop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTittle.setText(" ");
                if (btnStop.getText().toString().equals("Cancel")) {
                 //   timer.onFinish();
                    timer.cancel();
                    btnStart.setText("Start");
                    btnStop.setText("Reset");
                    pincers.setEnabled(true);
                    npHours.setEnabled(true);
                    npMinutes.setEnabled(true);
                    npSeconds.setEnabled(true);
                    textViewTime.setVisibility(View.VISIBLE);
                    setTimeOption = 1;
                    end = 1;
                } else {
                    btnStart.setText("Start");
                    npHours.setValue(0);
                    npMinutes.setValue(0);
                    npSeconds.setValue(0);
                    textViewTime.setText("00:00:00");
                    timer.cancel();
                    pincers.setEnabled(true);
                    npHours.setEnabled(true);
                    npMinutes.setEnabled(true);
                    npSeconds.setEnabled(true);
                    textViewTime.setVisibility(View.VISIBLE);
                    setTimeOption = 1;
                    end = 1;
                }
            }
        });
        btnRounds.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edRounds.getText().toString().isEmpty())
                {
                    rounds = 1;
                    Toast toast = Toast.makeText(getContext(),
                            "Rounds " + " " + "1" , Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                    nrounds = rounds;
                }
                else
                {
                    Toast toast = Toast.makeText(getContext(),
                        "Rounds " + " " + edRounds.getText().toString() , Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                    rounds = Integer.parseInt(edRounds.getText().toString());
                    edRounds.setText("");
                    nrounds = rounds;
                }

            }
        });
        btnPrepare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((npHours.getValue()==0)&&(npMinutes.getValue()==0)&&(npSeconds.getValue()==0))
                {
                    SportTimer.parts[0] = 0;
                    SportTimer.parts[1] = 0;
                    SportTimer.parts[2] = 5;
                    Toast toast = Toast.makeText(getContext(),
                            "Preparing time: " + " "+ "hours:" +  " "+ "0" + "  ||  "  + "minutes:" + " "+ "0"+ "  ||  " + "seconds:" + " "+ "0"  , Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                }
                else
                {
                    SportTimer.parts[0] = npHours.getValue();
                    SportTimer.parts[1] = npMinutes.getValue();
                    SportTimer.parts[2] = npSeconds.getValue();
                    Toast toast = Toast.makeText(getContext(),
                            "Preparing time: " + " "+ "hours:" +  " "+ npHours.getValue() + "  ||  "  + "minutes:" + " "+ npMinutes.getValue()+ "  ||  " + "seconds:" + " "+ npSeconds.getValue()  , Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                }
                SportTimer.full[0] = (1000 * (60 * (60 *  SportTimer.parts[0])) + 1000 * (60 *  SportTimer.parts[1]) + 1000 * ( SportTimer.parts[2]) + 500);
                mfull[0] =  SportTimer.full[0];
            }
        });
        btnWork.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((npHours.getValue() == 0) && (npMinutes.getValue() == 0) && (npSeconds.getValue() == 0)) {
                    SportTimer.parts1[0] = 0;
                    SportTimer.parts1[1] = 0;
                    SportTimer.parts1[2] = 10;
                    Toast toast = Toast.makeText(getContext(),
                            "Preparing time: " + " "+ "hours:" +  " "+ "0" + "  ||  "  + "minutes:" + " "+ "0"+ "  ||  " + "seconds:" + " "+ "10"  , Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                } else {
                    SportTimer.parts1[0] = npHours.getValue();
                    SportTimer.parts1[1] = npMinutes.getValue();
                    SportTimer.parts1[2] = npSeconds.getValue();
                    Toast toast = Toast.makeText(getContext(),
                            "Working time: " + " "+ "hours:" + " " + npHours.getValue()+ "  ||  "  + "minutes:" + " "+ npMinutes.getValue() + "  ||  "  + "seconds:" + " "+ npSeconds.getValue()  , Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                }
                SportTimer.full[1] = (1000 * (60 * (60 * SportTimer.parts1[0])) + 1000 * (60 * SportTimer.parts1[1]) + 1000 * (SportTimer.parts1[2]) + 500);
                mfull[1] =  SportTimer.full[1];
             }
        });
        btnRest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((npHours.getValue()==0)&&(npMinutes.getValue()==0)&&(npSeconds.getValue()==0))
                {
                    SportTimer.parts2[0] = 0;
                    SportTimer.parts2[1] = 0;
                    SportTimer.parts2[2] = 5;
                    Toast toast = Toast.makeText(getContext(),
                            "Preparing time: " + " "+ "hours:" +  " "+ "0" + "  ||  "  + "minutes:" + " "+ "0"+ "  ||  " + "seconds:" + " "+ "0"  , Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(getContext(),
                        "Resting time: " + " "+ "hours:" + " "+ npHours.getValue() + "  ||  " + "minutes:" + " " + npMinutes.getValue() + "  ||  " + "seconds:" + " "+ npSeconds.getValue()  , Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                    SportTimer.parts2[0] = npHours.getValue();
                    SportTimer.parts2[1] = npMinutes.getValue();
                    SportTimer.parts2[2] = npSeconds.getValue();
                }
                SportTimer.full[2] = (1000 * (60 * (60 *  SportTimer.parts2[0])) + 1000 * (60 *  SportTimer.parts2[1]) + 1000 * ( SportTimer.parts2[2]) + 500);
                mfull[2] =  SportTimer.full[2];
              }
        });
        return v;
    }
    private void savePref(long sRes, long sRes2, long sRes3, int sRes4, String sRes5){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        SharedPreferences.Editor editor3 = sharedPreferences3.edit();
        SharedPreferences.Editor editor4 = sharedPreferences4.edit();
        SharedPreferences.Editor editor5 = sharedPreferences5.edit();
        editor.putLong(PREF_NAME, sRes);
        editor.apply();
        editor2.putLong(PREF_NAME2, sRes2);
        editor2.apply();
        editor3.putLong(PREF_NAME3, sRes3);
        editor3.apply();
        editor4.putInt(PREF_NAME4, sRes4);
        editor4.apply();
        editor5.putString(PREF_NAME5, sRes5);
        editor5.apply();
    }

    private long loadPref(){
        sharedPreferences = getActivity().getSharedPreferences(PREF_NAME , Context.MODE_PRIVATE);
        long svaVal = sharedPreferences.getLong(PREF_NAME, SportTimer.full[0]);
        return svaVal;
    }
    private long loadPref2(){
        sharedPreferences2 = getActivity().getSharedPreferences(PREF_NAME2 , Context.MODE_PRIVATE);
        long svaVal2 = sharedPreferences2.getLong(PREF_NAME2, SportTimer.full[1]);
        return svaVal2;
    }
    private long loadPref3(){
        sharedPreferences3 = getActivity().getSharedPreferences(PREF_NAME3 , Context.MODE_PRIVATE);
        long svaVal3 = sharedPreferences3.getLong(PREF_NAME3, SportTimer.full[2]);
        return svaVal3;
    }
    private int loadPref4(){
        sharedPreferences4 = getActivity().getSharedPreferences(PREF_NAME4 , Context.MODE_PRIVATE);
        int svaVal4 = sharedPreferences4.getInt(PREF_NAME4, rounds);
        return svaVal4;
    }
    private String loadPref5(){
        sharedPreferences5 = getActivity().getSharedPreferences(PREF_NAME5 , Context.MODE_PRIVATE);
        String svaVal5 = sharedPreferences5.getString(PREF_NAME5, "");
        return svaVal5;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        savePref(SportTimer.full[0], SportTimer.full[1], SportTimer.full[2],rounds,txtTittle.getText().toString());
    }


    @Override
    public void onResume() {
        super.onResume();
        SportTimer.full[0] = loadPref();
        SportTimer.full[1] = loadPref2();
        SportTimer.full[2] = loadPref3();
        txtTittle.setText(loadPref5());
        rounds = loadPref4();
    }

    @Override
    public void onStart() {
        super.onStart();
        SportTimer.full[0] = loadPref();
        SportTimer.full[1] = loadPref2();
        SportTimer.full[2] = loadPref3();
        txtTittle.setText(loadPref5());
        rounds = loadPref4();
    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        public void onFinish() {

            if ((setTimeOption == 2)&&(rounds > 0)){
                txtTittle.setText(" Round/set:"+ (rounds));
                timer2 = new CounterClass(SportTimer.full[1], 1000);
                timer2.start();
                setTimeOption = 3;
                mPlayerstart.start();
            }
            else if ((setTimeOption == 3)&&(rounds > 0)){
                timer3 = new CounterClass(SportTimer.full[2], 1000);
                timer3.start();
                txtTittle.setText(" Resting time: ");
                setTimeOption = 4;
                rounds = rounds - 1;
                mPlayerstart.start();
            }
            else if ((setTimeOption == 4)&&(rounds > 0)){
                timer4 = new CounterClass(SportTimer.full[0], 1000);
                timer4.start();
                txtTittle.setText(" Preparing time: ");
                setTimeOption = 2;
                mPlayerstart.start();
            }
            else if ((rounds ==  0)||(end == 1)) {
                setTimeOption = 1;
                npHours.setEnabled(true);
                npMinutes.setEnabled(true);
                npSeconds.setEnabled(true);
                textViewTime.setText("Good job! ;)");
                btnStart.setText("Start");
                btnStop.setText("Reset");
                txtTittle.setText(" Timer finished! ;) ");
                Context context1 = getContext();
                Intent notificationIntent1 = new Intent();
                PendingIntent contentIntent1 = PendingIntent.getActivity(context1,
                        0, notificationIntent1,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationManager nm1 = (NotificationManager) context1
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                Resources res1 = context1.getResources();
                Notification.Builder builder1 = new Notification.Builder(context1);
                // Uri ringURI1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                mPlayerfinish.start();
                long[] vibrate1 = new long[]{1000, 1000};
                builder1.setContentIntent(contentIntent1)
                        .setTicker("You did it!?!")
                        .setSmallIcon(R.drawable.clocksicon)
                        .setWhen(System.currentTimeMillis())
                        //  .setSound(ringURI1)
                        .setVibrate(vibrate1)
                        .setContentTitle(" Notification ")
                        .setContentText(" Timer was finished! Good job ;)");
                Notification notification1 = builder1.build();
                notification1.defaults = Notification.DEFAULT_SOUND |
                        Notification.DEFAULT_VIBRATE;
                notification1.flags = notification1.flags | Notification.FLAG_SHOW_LIGHTS;
                notification1.flags = notification1.flags | Notification.FLAG_INSISTENT;
                Notification n1 = builder1.getNotification();
                nm1.notify(1, n1);
            }
        }
        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            textViewTime.setText(hms);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        savePref(SportTimer.full[0], SportTimer.full[1], SportTimer.full[2],rounds,txtTittle.getText().toString());
    }

    @Override
    public void onPause() {
        super.onPause();
        savePref(SportTimer.full[0], SportTimer.full[1], SportTimer.full[2],rounds,txtTittle.getText().toString());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

  /* @Override
    public void onAttach(Context context) {
       super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
           throw new RuntimeException(context.toString()
                  + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
