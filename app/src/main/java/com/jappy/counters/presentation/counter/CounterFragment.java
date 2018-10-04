package com.jappy.counters.presentation.counter;

import com.jappy.counters.R;
import com.jappy.counters.base.BaseFragment;
import com.jappy.counters.base.ViewModelFactory;
import com.jappy.counters.databinding.FragmentCounterBinding;
import com.jappy.counters.domain.Counter;
import com.jappy.counters.presentation.EventListener;
import com.jappy.counters.presentation.counter.adapter.CounterListAdapter;
import com.jappy.counters.presentation.utils.CountDrawable;
import com.jappy.counters.presentation.utils.Response;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CounterFragment extends BaseFragment<FragmentCounterBinding> implements EventListener {

    @Inject ViewModelFactory viewModelFactory;
    @Inject CounterListAdapter adapter;
    private CounterViewModel viewModel;
    MenuItem menuItem;
    MenuItem menuItemBin;

    public CounterFragment() {
        // Required empty public constructor
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_counter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().invalidateOptionsMenu();
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CounterViewModel.class);
        binder.recyclerView.setAdapter(adapter);
        adapter.setListener(this);
        binder.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.getResponse().observe(this, response -> processResponse(response));
        viewModel.getTotal().observe(this, total -> updateTotal(total));
        viewModel.getMessage().observe(this, showMessage -> processMessage(showMessage));

        binder.totalContainer.ivActionAdd.setOnClickListener(view12 -> {
            addAnimation();
        });

        binder.totalContainer.ivActionCheck.setOnClickListener(view1 -> {
            hideSoftKeyboard(getActivity());
            if (validCounterName(binder.totalContainer.tiAddItem.getText().toString())) {
                viewModel.addCounter(binder.totalContainer.tiAddItem.getText().toString());
            }
            checkAnimation();
        });

        binder.totalContainer.tiAddItem.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideSoftKeyboard(getActivity());

                if (validCounterName(binder.totalContainer.tiAddItem.getText().toString())) {
                    viewModel.addCounter(binder.totalContainer.tiAddItem.getText().toString());
                }
                checkAnimation();
                return true;
            }
            return false;
        });
    }

    public boolean validCounterName(final String name) {
        if ((name.length() > 0 && name.length() < 2)) {
            showMessage(getResources().getString(R.string.valid_counter_name));
            return false;
        }
        return true;
    }

    public void addAnimation() {
        Animation animation;
        animation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.left_anim);
        binder.totalContainer.llTotal.setVisibility(View.GONE);
        binder.totalContainer.llInput.setVisibility(View.VISIBLE);
        binder.totalContainer.llInput.startAnimation(animation);
    }

    public void checkAnimation() {
        Animation animation;
        animation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.left_anim);
        binder.totalContainer.llTotal.setVisibility(View.VISIBLE);
        binder.totalContainer.llInput.setVisibility(View.GONE);
        binder.totalContainer.llTotal.startAnimation(animation);
        binder.totalContainer.tiAddItem.setText("");
    }

    private void updateTotal(final String total) {
        setCount(total);
        binder.totalContainer.tvTotalCount.setText(total);
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void processResponse(Response response) {
        switch (response.status) {
            case LOADING:
                LoadingState(true);
                break;

            case SUCCESS:
                setData(response.data);

                break;

            case ERROR:
                ErrorState(response.error);
                break;
        }
    }

    private void ErrorState(final Throwable error) {
        LoadingState(false);
        binder.llContainer.setVisibility(View.GONE);
        binder.emptyContainer.emptyLayout.setVisibility(View.VISIBLE);
        binder.emptyContainer.tvMessage.setText(error.getMessage());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menuItem = menu.findItem(R.id.ic_group);
        menuItemBin = menu.findItem(R.id.ic_bin);
        menuItemBin.setOnMenuItemClickListener(menuItem -> {
            viewModel.deleteAllCounter();
            return false;
        });
        setCount("0");
    }

    public void setCount(String count) {

        if (getActivity() != null && isAdded()) {

            LayerDrawable icon = (LayerDrawable) menuItem.getIcon();
            CountDrawable badge;

            // Reuse drawable if possible
            Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
            if (reuse != null && reuse instanceof CountDrawable) {
                badge = (CountDrawable) reuse;
            } else {
                badge = new CountDrawable(getActivity());
            }

            badge.setCount(count);
            icon.mutate();
            icon.setDrawableByLayerId(R.id.ic_group_count, badge);
        }
    }

    private void LoadingState(boolean isVisibility) {
        if (isVisibility) {
            binder.llContainer.setVisibility(View.GONE);
            binder.emptyContainer.emptyLayout.setVisibility(View.GONE);
            binder.loadingContainer.loadingContainer.setVisibility(View.VISIBLE);
        } else {
            binder.llContainer.setVisibility(View.VISIBLE);
            binder.loadingContainer.loadingContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void increaseCounter(final Counter item) {
        viewModel.increaseCounter(item);
    }

    @Override
    public void decreaseCounter(final Counter item) {
        viewModel.decreaseounter(item);
    }

    @Override
    public void removeCounter(final Counter item) {
        viewModel.removeCounter(item);
    }

    private void setData(final List<Counter> counter) {
        adapter.setList(counter, true);
        LoadingState(false);
        if (counter.size() == 0) {
            binder.emptyContainer.emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    private void processMessage(Response response) {
        if (response != null) {
            switch (response.message) {
                case ADD:
                    showMessage(getResources().getString(R.string.add_message));
                    break;

                case NOACTION:
                    showMessage(getResources().getString(R.string.noaction_message));
                    break;
                case REMOVE:
                    showMessage(getResources().getString(R.string.remove_message));
                    break;
            }
        }
    }

    public void showMessage(String msj) {
        Snackbar.make(binder.coordinatorLayout, msj, Snackbar.LENGTH_SHORT)
                .show();
    }
}
