package com.ashleigh.myalarm;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ashleigh.myalarm.adapter.AlarmRecyclerAdapter;
import com.ashleigh.myalarm.data.AlarmController;
import com.ashleigh.myalarm.data.DbHelper;
import com.ashleigh.myalarm.model.Alarm;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AlarmFragment extends Fragment implements AlarmRecyclerAdapter.SwitchClickListener {

    private static final String TAG = AlarmFragment.class.getSimpleName();

    private AlarmRecyclerAdapter adapter;
    private LinearLayoutManager manager;
    private RecyclerView recyclerView;
    private FloatingActionButton addButton;
    private TextView emptyView;
    private ArrayList<Alarm> arrayList = new ArrayList<>();
    private DbHelper dbHelper;
    private AlarmController controller;

    public AlarmFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_alarm, container, false);

        recyclerView = rootView.findViewById(R.id.alarm_RV);
        addButton = rootView.findViewById(R.id.alarm_FAB);
        emptyView = rootView.findViewById(R.id.empty_alarmView);
        dbHelper = DbHelper.getInstance();
        controller = new AlarmController();

        manager = new LinearLayoutManager(getActivity());
        adapter = new AlarmRecyclerAdapter(getContext(), this);

        recyclerView.setLayoutManager(manager);
        //recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AlarmEditorActivity.class);
                startActivity(intent);
            }
        });

        updateUI();

        return rootView;
    }

    public void updateUI() {
        if (adapter.getItemCount() < 1) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void OnSwitchClicked(View view, boolean isChecked, Alarm model) {
        if (isChecked) {
            AlarmController.addAlarm(getContext(), model);
        }
    }
}
