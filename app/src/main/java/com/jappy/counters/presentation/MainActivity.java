package com.jappy.counters.presentation;

import com.jappy.counters.R;
import com.jappy.counters.base.BaseActivity;
import com.jappy.counters.databinding.ActivityMainBinding;
import com.jappy.counters.presentation.counter.CounterFragment;
import com.jappy.counters.presentation.utils.CountDrawable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends BaseActivity<ActivityMainBinding> {



    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.screenContainer, new CounterFragment()).commit();
        }
    }
}
