package com.hzjytech.hades.swipetpdismissdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Hades on 2016/6/17.
 */
public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    private ArrayList<String> items = new ArrayList<>();

    public BaseAdapter() {
        setHasStableIds(true);
    }

    public void add(String object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, String object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(Collection<String> collection) {
        if (collection != null) {
            items.clear();
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void addAll(String... items) {
        addAll(Arrays.asList(items));
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(String object) {
        items.remove(object);
        notifyDataSetChanged();
    }

    public String getItem(int position) {

        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addAllItem(List<String> tempData) {
        if(tempData!=null){
            items.addAll(tempData);
            notifyDataSetChanged();
        }
    }
}