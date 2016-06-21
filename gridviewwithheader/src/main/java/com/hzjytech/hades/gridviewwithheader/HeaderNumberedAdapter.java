package com.hzjytech.hades.gridviewwithheader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 2016/6/21.
 */
public class HeaderNumberedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int CONTENT = 1;
    private final Context mContext;
    List<CustomEntity> entities = new ArrayList<>();

    public HeaderNumberedAdapter(Context context, List<CustomEntity> entities) {
        this.mContext = context;

        this.entities = cut(entities);
    }

    private List<CustomEntity> cut(List<CustomEntity> entities) {
        List<CustomEntity> tempEntity = new ArrayList<>();
        String begin = "#";

        for (CustomEntity customEntity : entities) {
            if (begin.equals(customEntity.getContent().substring(0, 1))) {


            } else {
                begin = customEntity.getContent().substring(0, 1);
                tempEntity.add(new CustomEntity(true, customEntity.getContent().substring(0, 1)));
            }
            tempEntity.add(new CustomEntity(false, customEntity.getContent()));
        }

        return tempEntity;
    }

    public boolean isHeader(int position) {
        return entities.get(position).isHeader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case HEADER:
                View v1 = inflater.inflate(R.layout.header, parent, false);
                holder = new HeaderViewHolder(v1);
                break;
            default:
                View v2 = inflater.inflate(R.layout.item, parent, false);
                holder = new ItemViewHolder(v2);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (entities.get(position).isHeader()) {
            ((HeaderViewHolder) holder).header.setText(entities.get(position).getContent());
        } else {
            ((ItemViewHolder) holder).text.setText(entities.get(position).getContent());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (entities.get(position).isHeader()) {
            return HEADER;
        } else {
            return CONTENT;
        }
    }

    public void addRefreshData(List<CustomEntity> data) {
        entities.clear();
        entities=cut(data);
        notifyDataSetChanged();
    }

    public void addMoreData(List<CustomEntity> data) {
        List<CustomEntity> tempData=cut(data);
        if(entities.get(entities.size()-1).getContent().startsWith(tempData.get(0).getContent())){
            tempData.remove(0);
        }
        entities.addAll(tempData);
        notifyDataSetChanged();
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView header;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.header);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView text;

        public ItemViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);

            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(getLayoutPosition());
                }
            });

        }
    }

    private void removeItem(int position) {
        if (position == entities.size() - 1) {
            if (entities.get(position - 1).isHeader()) {
                entities.remove(position);
                entities.remove(position - 1);
            } else {
                entities.remove(position);
            }
        } else if (entities.get(position - 1).isHeader() && entities.get(position + 1).isHeader()) {
            entities.remove(position);
            entities.remove(position - 1);
        } else {
            entities.remove(position);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return entities.size();
    }
}
