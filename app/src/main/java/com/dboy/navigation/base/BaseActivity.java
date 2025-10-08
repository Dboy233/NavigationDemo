package com.dboy.navigation.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.dboy.navigation.utils.ReflexUtil;

public class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB viewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ReflexUtil.INSTANCE.reflexViewBinding(getClass(), getLayoutInflater());
        setContentView(viewBinding.getRoot());
    }
}
