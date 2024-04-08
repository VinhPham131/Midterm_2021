package com.example.midterm_2021;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<HistoryItem> historyList;
    private OnItemDeleteListener onItemDeleteListener;



    public HistoryAdapter(List<HistoryItem> historyList, OnItemDeleteListener onItemDeleteListener) {
        this.historyList = historyList;
        this.onItemDeleteListener = onItemDeleteListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryItem historyItem = historyList.get(position);
        holder.tvInput.setText(historyItem.getInput());
//        holder.tvAction.setText(historyItem.getAction());
        holder.tvOutput.setText(historyItem.getOutput());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    removeItem(position);
                }
        }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInput, tvAction, tvOutput;
        ImageButton btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInput = itemView.findViewById(R.id.tv_input);
            tvAction = itemView.findViewById(R.id.tv_action);
            tvOutput = itemView.findViewById(R.id.tv_output);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
    public void removeItem(int position) {
        if (position >= 0 && position < historyList.size()) {
            historyList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, historyList.size());
        }
    }
}
