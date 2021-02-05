package com.dboy.navigation.fragment.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

/**
 * @author DBoy
 * @date 2020/12/20
 * Class 描述 :
 */
public class BaseViewModel extends ViewModel {
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("ViewModel", "onCleared: "+getClass().getSimpleName());
    }
}
