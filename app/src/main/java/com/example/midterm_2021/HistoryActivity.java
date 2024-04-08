package com.example.midterm_2021;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements OnItemDeleteListener{
    private RecyclerView historyRecyclerView;
    private HistoryAdapter historyAdapter;
    private ArrayList<HistoryItem> historyList;
    private ImageButton btnBack;
    private ImageButton btnDeleteAll;
    private MyViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        btnBack = findViewById(R.id.btn_back);
        btnDeleteAll = findViewById(R.id.btn_delete);
        historyRecyclerView = findViewById(R.id.lv_history);
        model = new ViewModelProvider(this).get(MyViewModel.class);
        model.getHistory().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> updatedHistoryList) {
                historyAdapter.notifyDataSetChanged();
            }
        });
        historyAdapter = new HistoryAdapter(historyList, this);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setAdapter(historyAdapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyList.clear();
                historyAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemDelete(int position) {
        model.removeHistoryItem(position);
    }
}