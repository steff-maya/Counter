package com.jappy.counters.presentation.counter;

import com.jappy.counters.R;
import com.jappy.counters.base.BaseFragment;
import com.jappy.counters.base.ViewModelFactory;
import com.jappy.counters.databinding.FragmentCounterBinding;
import com.jappy.counters.domain.Counter;
import com.jappy.counters.presentation.EventListener;
import com.jappy.counters.presentation.counter.adapter.CounterListAdapter;
import com.jappy.counters.presentation.utils.RecyclerItemTouchHelper;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CounterFragment extends BaseFragment<FragmentCounterBinding> implements EventListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    @Inject ViewModelFactory viewModelFactory;
    @Inject CounterListAdapter adapter;
    private CounterViewModel viewModel;

    public CounterFragment() {
        // Required empty public constructor
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_counter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CounterViewModel.class);
        binder.recyclerView.setAdapter(adapter);
        adapter.setListener(this);
        binder.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binder.bottomLayout.ivActionAdd.setOnClickListener(view12 -> {
            Animation animation;
            animation = AnimationUtils.loadAnimation(getActivity(),
                    R.anim.left_anim);
            binder.bottomLayout.llTotal.setVisibility(View.GONE);
            binder.bottomLayout.llInput.setVisibility(View.VISIBLE);
            binder.bottomLayout.llInput.startAnimation(animation);
        });

        binder.bottomLayout.ivActionCheck.setOnClickListener(view1 -> {
            hidewSoftKeyboard(view1);
            Animation animation;
            animation = AnimationUtils.loadAnimation(getActivity(),
                    R.anim.left_anim);
            binder.bottomLayout.llTotal.setVisibility(View.VISIBLE);
            binder.bottomLayout.llInput.setVisibility(View.GONE);
            binder.bottomLayout.llTotal.startAnimation(animation);
            if (binder.bottomLayout.tiAddItem.getText().length() > 0) {
                viewModel.addCounter(binder.bottomLayout.tiAddItem.getText().toString());
                binder.bottomLayout.tiAddItem.setText("");
            }
        });

        initViewModel();

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binder.recyclerView);
    }

    public void hidewSoftKeyboard(View view) {
        InputMethodManager inputManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void  showMessage(String msj){
        Snackbar.make(binder.coordinatorLayout, msj, Snackbar.LENGTH_SHORT)
                .show();
    }

    private void initViewModel() {
        viewModel.getCounters().observe(this, counter -> {
            if (counter != null) {
                binder.recyclerView.setVisibility(View.VISIBLE);
                adapter.setList(counter, true);
            }
        });
        viewModel.getTotal().observe(this, counter -> {
            if (counter != null) {
                binder.bottomLayout.tvTotalCount.setText(String.valueOf(counter));
            }
        });

        viewModel.getcounterOperationError().observe(this, isError -> {
            if (isError != null) {
                if (isError) {
                   showMessage(getActivity().getResources().getString(R.string.counter_error));
                }
            }
        });

        viewModel.getError().observe(this, isError -> {
            if (isError != null) {
                if (isError) {
                    binder.tvError.setVisibility(View.VISIBLE);
                    binder.recyclerView.setVisibility(View.GONE);
                    binder.tvError.setText("An Error Occurred While Loading Data!");
                } else {
                    binder.tvError.setVisibility(View.GONE);
                    binder.tvError.setText(null);
                }
            }
        });

        viewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                binder.loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    binder.tvError.setVisibility(View.GONE);
                    binder.recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void increaseCounter(final Counter item) {
        viewModel.increaseCounter(item);
    }

    @Override
    public void decreaseounter(final Counter item) {
        viewModel.decreaseounter(item);
    }

    @Override
    public void removeCounter(final Counter item) { viewModel.removeCounter(item); }

    @Override
    public void seletecCounter(final Counter item) { }

    @Override
    public void onSwiped(final ViewHolder viewHolder, final int direction, final int position) {
        viewModel.removeCounter(adapter.getItem(position));
    }
}
