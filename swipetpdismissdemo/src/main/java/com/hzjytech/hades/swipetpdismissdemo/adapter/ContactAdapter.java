package com.hzjytech.hades.swipetpdismissdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzjytech.hades.swipetpdismissdemo.MainActivity;
import com.hzjytech.hades.swipetpdismissdemo.R;
import com.hzjytech.hades.swipetpdismissdemo.widget.SwipeItemLayout;

import java.io.ObjectInputValidation;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 2016/6/17.
 */
public class ContactAdapter extends BaseAdapter<ContactAdapter.ContactViewHolder> {
    /**
     * 当前处于打开状态的item
     */
    private List<SwipeItemLayout> mOpenedSil = new ArrayList<SwipeItemLayout>();

    private List<String> mLists = new ArrayList<>();
    private ContactAdapterSwipeClickListener mListener;

    public interface  ContactAdapterSwipeClickListener{
        void onSwipeClickListener(int position);
    }

    private Context mContext;

    public ContactAdapter(Context ct, List<String> mLists) {
        this.mLists = mLists;
        mContext = ct;

        Log.d("contactAdapter", "size" + mLists.size());
        this.addAll(mLists);
    }

    public void setListener(ContactAdapterSwipeClickListener listener){
        this.mListener=listener;
    }

    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ContactViewHolder holder, final int position) {
        SwipeItemLayout swipeRoot = holder.mRoot;
        swipeRoot.setSwipeAble(true);
        swipeRoot.setDelegate(new SwipeItemLayout.SwipeItemLayoutDelegate() {
            @Override
            public void onSwipeItemLayoutOpened(SwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
                mOpenedSil.add(swipeItemLayout);
            }

            @Override
            public void onSwipeItemLayoutClosed(SwipeItemLayout swipeItemLayout) {
                mOpenedSil.remove(swipeItemLayout);
            }

            @Override
            public void onSwipeItemLayoutStartOpen(SwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
            }
        });
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mListener!=null){
                    mListener.onSwipeClickListener(position);
                }
            }
        });

        TextView textView = holder.mName;
        textView.setText(getItem(position));

    }

    public void closeOpenedSwipeItemLayoutWithAnim() {
        for (SwipeItemLayout sil : mOpenedSil) {
            sil.closeWithAnim();
        }
        mOpenedSil.clear();
    }


    public class ContactViewHolder extends RecyclerView.ViewHolder {

        public TextView mName;
        public SwipeItemLayout mRoot;
        public TextView mDelete;

        public ContactViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.item_contact_title);
            mRoot = (SwipeItemLayout) itemView.findViewById(R.id.item_contact_swipe_root);
            mDelete = (TextView) itemView.findViewById(R.id.item_contact_delete);
        }
    }
}