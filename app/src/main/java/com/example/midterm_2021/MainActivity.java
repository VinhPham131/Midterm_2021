package com.example.midterm_2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.midterm_2021.databinding.ActivityMainBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemDeleteListener {
    private ActivityMainBinding binding;
    private MyViewModel model;
    private ArrayList<String> historyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        model = new ViewModelProvider(this).get(MyViewModel.class);

        model.getResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String resultString) {
                binding.tvResult.setText(resultString);
            }
        });
        model.getHistory().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> updatedHistoryList) {
                historyList = new ArrayList<>(updatedHistoryList);
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = binding.etInput.getText().toString().toLowerCase();
                String actionText = binding.etAction.getText().toString().toLowerCase();
                if (inputText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                } else {
                    if (actionText.contains("count letter digit")) {
                        final int letterCount = countLetter(inputText);
                        final int digitCount = countDigit(inputText);
                        String result = model.setResultCount(letterCount, digitCount);
                        String historyEntry = "Input: " + inputText + "\nAction: " + "count letter digit" + "\nResult: " + result;
                        if (!historyList.contains(historyEntry)) {
                            addHistoryEntry(inputText, "count letter digit", result);
                        }
                    } else if (actionText.contains("remove even")) {
                        final String remove = removeEvenNumbers(inputText);
                        String result = model.setResultRemove(remove);
                        addHistoryEntry(inputText, "remove even", remove);
                    }
                }
            }
        });
        binding.btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                intent.putStringArrayListExtra("historyList", historyList);
                startActivity(intent);
            }
        });

    }
    private  int countLetter(String input) {
        int letterCount = 0;
        for (int i = 0; i < input.length(); i++) {
            if(Character.isLetter(input.charAt(i))) {
                letterCount++;
            }
        }
        return letterCount;
    }
    public int countDigit(String input) {
        int countDigit = 0;
        for(int i = 0; i < input.length(); i++) {
            if(Character.isDigit(input.charAt(i))) {
                countDigit++;
            }
        }
        return countDigit;
    }
    private void addHistoryEntry(String input, String action, String result) {
        String historyEntry = "Input: " + input + "\nAction: " + action + "\nResult: " + result;
        model.addHistory(historyEntry);
    }
    private String removeEvenNumbers(String input) {
        StringBuilder sb = new StringBuilder();
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                if (number.length() != 0) {
                    int num = Integer.parseInt(number.toString());
                    if (num % 2 != 0) {
                        sb.append(num);
                    }
                    number = new StringBuilder();
                }
                sb.append(c);
            }
        }
        if (number.length() != 0) {
            int num = Integer.parseInt(number.toString());
            if (num % 2 != 0) {
                sb.append(num);
            }
        }
        return sb.toString();
    }
    @Override
    public void onItemDelete(int position) {
        if (position >= 0 && position < historyList.size()) {
            model.removeHistoryItem(position);
            historyList.remove(position);

        }
    }
}
