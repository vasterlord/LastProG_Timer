package timerbench.com.timerbench.resivers;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import timerbench.com.timerbench.R;

/**
 * Created by Ivan on 14.05.2016.
 */
public class RingAlarm extends Activity implements View.OnClickListener {
private Button okButton;
private TextView messageTetxtView;
private MediaPlayer mp;
private Uri notification;

@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popupdialog);
        messageTetxtView = (TextView) findViewById(R.id.textViewMessage);
        okButton = (Button) findViewById(R.id.popCancelB);
        okButton.setOnClickListener(this);

        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mp = MediaPlayer.create(getApplicationContext(), notification);
        mp.setLooping(true);
        mp.start();

        String message = getIntent().getExtras().getString("message", "Alarm started");

        messageTetxtView.setText(message);
        }

@Override
public void onClick(View arg0) {
        stopPlaying();
        finish();
        }

private void stopPlaying() {
        if (mp != null) {
        mp.stop();
        mp.release();
        mp = null;
        }
        }

@Override
protected void onPause() {
        super.onPause();
        stopPlaying();
        }
        }
