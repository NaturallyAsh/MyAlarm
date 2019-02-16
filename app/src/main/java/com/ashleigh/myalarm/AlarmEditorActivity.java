package com.ashleigh.myalarm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ashleigh.myalarm.data.DbHelper;
import com.ashleigh.myalarm.model.Alarm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AlarmEditorActivity extends AppCompatActivity {

    private static final String TAG = AlarmEditorActivity.class.getSimpleName();

    @BindView(R.id.editor_timepicker)
    TimePicker timePicker;
    @BindView(R.id.alarm_editor_nameET)
    EditText nameET;
    @BindView(R.id.monTV)
    TextView monTV;
    @BindView(R.id.tueTV)
    TextView tueTV;
    @BindView(R.id.wedTV)
    TextView wedTV;
    @BindView(R.id.thursTV)
    TextView thursTv;
    @BindView(R.id.friTV)
    TextView friTV;
    @BindView(R.id.satTV)
    TextView satTV;
    @BindView(R.id.sunTV)
    TextView sunTV;
    @BindView(R.id.alarm_editor_toolbar)
    Toolbar toolbar;
    @BindView(R.id.editor_save_button)
    Button saveBT;
    @BindView(R.id.editor_cancel_button)
    Button cancelBT;
    private ArrayList<Integer> daysOfWeek = new ArrayList<>();
    private String list = null;
    private DbHelper dbHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_alarm);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = DbHelper.getInstance();

        monTV.setOnClickListener(clickListener);
        tueTV.setOnClickListener(clickListener);
        wedTV.setOnClickListener(clickListener);
        thursTv.setOnClickListener(clickListener);
        friTV.setOnClickListener(clickListener);
        satTV.setOnClickListener(clickListener);
        sunTV.setOnClickListener(clickListener);

        saveBT.setOnClickListener(exitListener);
        cancelBT.setOnClickListener(exitListener);

        Log.i(TAG, "timepicker hour: " + timePicker.getHour() +
                " timepicker min: " + timePicker.getMinute());

    }

    private String putArray(ArrayList<Integer> arrayList) {

        try {
            JSONObject json = new JSONObject();
            json.put("daysArray", new JSONArray(arrayList));
            list = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void getArray() {

        JSONArray getList = null;
        try {
            JSONObject json = new JSONObject(list);
            getList = json.optJSONArray("daysArray");
            if (getList != null) {
                for (int i = 0; i < getList.length(); i++) {
                    Log.i(TAG, "array data: " + getList.getInt(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.i(TAG, "get list: " + getList);

    }

    private void onSave() {
        String name = nameET.getText().toString();
        int hour = timePicker.getHour();
        int min = timePicker.getMinute();
        String daysList = putArray(daysOfWeek);


        Alarm alarm = new Alarm(name, hour, min, daysList);

        dbHelper.addAlarm(alarm);

    }

    private View.OnClickListener exitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.editor_save_button:
                    onSave();
                    finish();
                    //break;
                case R.id.editor_cancel_button:
                    onBackPressed();
                    break;
                case android.R.id.home:
                    onBackPressed();
                    break;
            }
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sunTV:
                    if (!sunTV.isSelected()) {
                        sunTV.setBackground(getDrawable(R.drawable.circle_color));
                        sunTV.setSelected(true);
                    } else {
                        sunTV.setBackground(getDrawable(R.drawable.circle));
                        sunTV.setSelected(false);
                    }
                    if (!dayExists(0)) {
                        daysOfWeek.add(0);
                    }
                    break;
                case R.id.monTV:
                    if (!monTV.isSelected()) {
                        monTV.setBackground(getDrawable(R.drawable.circle_color));
                        monTV.setSelected(true);
                    } else {
                        monTV.setBackground(getDrawable(R.drawable.circle));
                        monTV.setSelected(false);
                    }
                    if (!dayExists(1)) {
                        daysOfWeek.add(1);
                    }
                    break;
                case R.id.tueTV:
                    if (!tueTV.isSelected()) {
                        tueTV.setBackground(getDrawable(R.drawable.circle_color));
                        tueTV.setSelected(true);
                    } else {
                        tueTV.setBackground(getDrawable(R.drawable.circle));
                        tueTV.setSelected(false);
                    }
                    if (!dayExists(2)) {
                        daysOfWeek.add(2);
                    }
                    break;
                case R.id.wedTV:
                    if (!wedTV.isSelected()) {
                        wedTV.setBackground(getDrawable(R.drawable.circle_color));
                        wedTV.setSelected(true);
                    } else {
                        wedTV.setBackground(getDrawable(R.drawable.circle));
                        wedTV.setSelected(false);
                    }
                    if (!dayExists(3)) {
                        daysOfWeek.add(3);
                    }
                    break;
                case R.id.thursTV:
                    if (!thursTv.isSelected()) {
                        thursTv.setBackground(getDrawable(R.drawable.circle_color));
                        thursTv.setSelected(true);
                    } else {
                        thursTv.setBackground(getDrawable(R.drawable.circle));
                        thursTv.setSelected(false);
                    }
                    if (!dayExists(4)) {
                        daysOfWeek.add(4);
                    }
                    break;
                case R.id.friTV:
                    if (!friTV.isSelected()) {
                        friTV.setBackground(getDrawable(R.drawable.circle_color));
                        friTV.setSelected(true);
                    } else {
                        friTV.setBackground(getDrawable(R.drawable.circle));
                        friTV.setSelected(false);
                    }
                    if (!dayExists(5)) {
                        daysOfWeek.add(5);
                    }
                    break;
                case R.id.satTV:
                    if (!satTV.isSelected()) {
                        satTV.setBackground(getDrawable(R.drawable.circle_color));
                        satTV.setSelected(true);
                    } else {
                        satTV.setBackground(getDrawable(R.drawable.circle));
                        satTV.setSelected(false);
                    }
                    if (!dayExists(6)) {
                        daysOfWeek.add(6);
                    }
                    break;

            }
            //Log.i(TAG, "array size: " + daysOfWeek.size());
            //putArray(daysOfWeek);
            //getArray();
        }
    };
    public boolean dayExists(Integer day) {
        for (int i = 0; i < daysOfWeek.size(); i++) {
            if (day.equals(daysOfWeek.get(i))) {
                daysOfWeek.remove(daysOfWeek.get(i));
                return true;
            }
        }
        return false;
    }
}
