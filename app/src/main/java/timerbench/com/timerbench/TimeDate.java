package timerbench.com.timerbench;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class TimeDate extends AppCompatActivity {
    private static final int REQUEST_CODE = 23;
    private DatePicker pickerDate;
    private TimePicker pickerTime;
    private TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        setTitle("Set date and time");
        pickerDate = (DatePicker) findViewById(R.id.datePicker);
        pickerTime = (TimePicker) findViewById(R.id.timePicker);
        messageTextView = (TextView) findViewById(R.id.textViewMessage);

        Calendar calendar = Calendar.getInstance();

        pickerDate.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);

        pickerTime.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        pickerTime.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }

    public void onCreateClick(View v) {
        Calendar current = Calendar.getInstance();

        Calendar cal = Calendar.getInstance();
        cal.set(pickerDate.getYear(),
                pickerDate.getMonth(),
                pickerDate.getDayOfMonth(),
                pickerTime.getCurrentHour(),
                pickerTime.getCurrentMinute(),
                00);

        if (cal.compareTo(current) <= 0) {
            //The set Date/Time already passed
            Toast.makeText(getApplicationContext(), "Invalid Date/Time", Toast.LENGTH_LONG).show();
        } else {
            setAlarm(cal);
        }
    }

    private void setAlarm(Calendar targetCal) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("calendar", targetCal);
        resultIntent.putExtra("message", messageTextView.getText().toString());
        setResult(RESULT_OK, resultIntent);
        finish();
    }


}
