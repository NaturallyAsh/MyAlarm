package com.ashleigh.myalarm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ashleigh.myalarm.R;
import com.ashleigh.myalarm.data.DbHelper;
import com.ashleigh.myalarm.model.Alarm;
import com.ashleigh.myalarm.utils.DateHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AlarmRecyclerAdapter extends RecyclerView.Adapter<AlarmRecyclerAdapter.ViewHolder> {

    private static final String TAG = AlarmRecyclerAdapter.class.getSimpleName();

    private Context mContext;
    private DbHelper dbHelper;
    private Alarm alarmModel;
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    private static final SimpleDateFormat SHORT_WEEK_DAYS_FORMAT = new SimpleDateFormat("E", Locale.getDefault());
    private SwitchClickListener listener;

    public AlarmRecyclerAdapter(Context context, SwitchClickListener listener) {
        this.mContext = context;
        dbHelper = DbHelper.getInstance();
        this.listener = listener;
    }

    public interface SwitchClickListener {
        void OnSwitchClicked(View view, boolean isChecked, Alarm model);
    }

    @Override
    public AlarmRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.alarm_item, parent,
                false));
    }

    @Override
    public void onBindViewHolder(final AlarmRecyclerAdapter.ViewHolder holder, int position) {
        alarmModel = getItem(position);

        StringBuilder builder = new StringBuilder();
        builder.append("Repeats on:");
        builder.append(" ");
        ArrayList<String> daysDates = DateHelper.getShortWeekDays();

        String list = alarmModel.getDayList();
        if (list != null) {
            JSONArray getList = null;
            try {
                JSONObject json = new JSONObject(list);
                getList = json.optJSONArray("daysArray");
                Log.i(TAG, "getlist: " + getList);
                if (getList != null) {
                    Calendar calendar = Calendar.getInstance();
                    for (int i = 0; i < getList.length(); i++) {
                        calendar.set(Calendar.DAY_OF_WEEK, getList.getInt(i));

                        Log.i(TAG, "DOW: " + calendar.get(Calendar.DAY_OF_WEEK));
                        if (daysDates.contains(SHORT_WEEK_DAYS_FORMAT.format(calendar.getTime()))){
                            builder.append(daysDates.get(getList.getInt(i)));
                            builder.append(" ");
                        }
                    }
                    holder.dateTV.setText(builder);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, alarmModel.getmHour());
        cal.set(Calendar.MINUTE, alarmModel.getmMin());

        String time = TIME_FORMAT.format(cal.getTime());

        holder.nameTV.setText(alarmModel.getmName());
        holder.timeTV.setText(time);
        holder.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.OnSwitchClicked(buttonView, isChecked, alarmModel);
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return dbHelper.getAlarmCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View view;
        @BindView(R.id.alarm_item_name)
        TextView nameTV;
        @BindView(R.id.alarm_item_time)
        TextView timeTV;
        @BindView(R.id.alarm_item_date)
        TextView dateTV;
        @BindView(R.id.alarm_item_switch)
        Switch mSwitch;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public Alarm getItem(int position) {
        return dbHelper.getAlarmAt(position);
    }
}
