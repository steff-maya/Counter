package com.jappy.counters.presentation.counter.adapter;

import com.jappy.counters.R;
import com.jappy.counters.databinding.CounterItemBinding;
import com.jappy.counters.domain.Counter;
import com.jappy.counters.presentation.EventListener;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class CounterListAdapter extends RecyclerView.Adapter<CounterListAdapter.CounterViewHolder> {

    private List<Counter> list = new ArrayList<>();
    private EventListener listener;

    @Inject
    public CounterListAdapter() {

    }

    public void setListener(EventListener eventListener) {
        this.listener = eventListener;
    }

    @Override
    public CounterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CounterItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.counter_item, parent, false);
        return new CounterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CounterViewHolder holder, final int position) {
        if (list.get(position) != null) {
            holder.binding.tvName.setText(list.get(position).title);
            holder.binding.tvLetter.setText(String.valueOf(list.get(position).title.substring(0,1).toUpperCase()+list.get(position).title.substring(1,2).toLowerCase()));
            holder.binding.tvCount.setText(String.valueOf(list.get(position).count));
            holder.binding.ivInc.setOnClickListener(view -> {
                listener.increaseCounter(list.get(position));
            });
            holder.binding.ivDec.setOnClickListener(view -> {
                if (list.get(position).getCount() > 0) {
                    listener.decreaseCounter(list.get(position));
                } else {
                    listener.removeCounter(list.get(position));
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Counter getItem(int pos) {
        return list.get(pos);
    }

    public void remove(int adapterPosition) {
        list.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    public void addAtFirst(Counter item) {
        list.add(0, item);
        notifyItemInserted(0);
    }

    public void add(Counter item) {
        list.add(item);
        notifyItemInserted(list.size() - 1);
    }

    public void setList(List<Counter> items, boolean notify) {
        list = items;
        Collections.reverse(list);
        if (notify) {
            notifyDataSetChanged();
        }
    }

    public class CounterViewHolder extends RecyclerView.ViewHolder {

        private final CounterItemBinding binding;

        public CounterViewHolder(final CounterItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
