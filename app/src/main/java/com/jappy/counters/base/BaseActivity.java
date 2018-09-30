package com.jappy.counters.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<BINDER extends ViewDataBinding> extends DaggerAppCompatActivity {
    protected BINDER binder;

    @LayoutRes
    protected abstract int layoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, layoutRes());
    }

    @Override
    protected void onDestroy() {
        if (binder != null) {
            binder.unbind();
        }
        super.onDestroy();
    }
}