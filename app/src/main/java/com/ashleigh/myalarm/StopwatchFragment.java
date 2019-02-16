package com.ashleigh.myalarm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StopwatchFragment extends Fragment {

    private static final String TAG = StopwatchFragment.class.getSimpleName();

    @BindView(R.id.start_button)
    Button startButton;
    @BindView(R.id.stop_button)
    Button stopButton;
    @BindView(R.id.reset_button)
    Button resetButton;
    @BindView(R.id.stopwatch)
    TextView stopWatchTV;
    Handler handler;
    private long milliSecTime, startTime, timeBuff, updateTime = 0L;
    private int secs, mins, milliSecs;

    public StopwatchFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        ButterKnife.bind(this, rootView);

        handler = new Handler();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                resetButton.setEnabled(false);

                startButton.setText(getString(R.string.resume_button_string));
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeBuff += milliSecTime;
                handler.removeCallbacks(runnable);
                resetButton.setEnabled(true);

                startButton.setText(getString(R.string.resume_button_string));
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                milliSecTime = 0L;
                startTime = 0L;
                timeBuff = 0L;
                updateTime = 0L;
                secs = 0;
                mins = 0;
                milliSecs = 0;

                stopWatchTV.setText(getString(R.string.stopwatch_string));
                startButton.setText(getString(R.string.start_button_string));
            }
        });

        return rootView;
    }

    public Runnable runnable = new Runnable() {
        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        @Override
        public void run() {
            milliSecTime = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + milliSecTime;
            secs = (int) (updateTime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            milliSecs = (int) (updateTime % 1000);

            stopWatchTV.setText("" + mins + ":"
                + String.format("%02d", secs) + ":"
                + String.format("%03d", milliSecs));

            handler.postDelayed(this, 0);
        }
    };
}
