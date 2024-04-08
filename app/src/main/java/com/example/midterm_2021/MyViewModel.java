package com.example.midterm_2021;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MyViewModel extends ViewModel {
    private MutableLiveData<String> result;
    private MutableLiveData<ArrayList<String>> historyList;

    public MyViewModel() {
        result = new MutableLiveData<>();
        result.setValue("");
        historyList = new MutableLiveData<>();
        historyList.setValue(new ArrayList<>());
    }
    public LiveData<String> getResult() {
        return result;
    }
    public LiveData<ArrayList<String>> getListView() {
        return historyList;
    }
    public String setResultCount(int letterCount, int digitCount) {
        String resultString =  "LETTERS " + letterCount + "\nDIGITS " + digitCount;
        result.setValue(resultString);
        return resultString;
    }
    public String setResultRemove(String remove) {
        result.setValue(remove);
        return remove;
    }
    public void addHistory(String resultString) {
        ArrayList<String> currentHistoryList = historyList.getValue();
        currentHistoryList.add(resultString);
        historyList.setValue(currentHistoryList);
    }
    public LiveData<ArrayList<String>> getHistory() {
        return historyList;
    }

    public void removeHistoryItem(int position) {
        if (position >= 0 && position < historyList.getValue().size()) {
            historyList.getValue().remove(position);
            historyList.setValue(historyList.getValue());
        }
    }
}