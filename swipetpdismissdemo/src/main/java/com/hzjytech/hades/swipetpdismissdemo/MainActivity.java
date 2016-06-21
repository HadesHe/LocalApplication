package com.hzjytech.hades.swipetpdismissdemo;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hzjytech.hades.swipetpdismissdemo.adapter.ContactAdapter;
import com.hzjytech.hades.swipetpdismissdemo.widget.DividerDecoration;
import com.hzjytech.hades.swipetpdismissdemo.widget.SuperSwipeRefreshLayout;
import com.hzjytech.hades.swipetpdismissdemo.widget.TouchableRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TouchableRecyclerView mRecyclerView;
    private ContactAdapter mAdapter;
    private List<String> mAllList = new ArrayList<>();
    private SuperSwipeRefreshLayout swipe;
    private ProgressBar progressBar;
    private ProgressBar footerProgressBar;
    private TextView textView;
    private ImageView imageView;
    private ImageView footerImageView;
    private TextView footerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mAllList.add("Name:        " + i);
        }
    }

    private void initView() {
        mRecyclerView = (TouchableRecyclerView) findViewById(R.id.contact_member);
        mAdapter = new ContactAdapter(this, mAllList);
        mAdapter.setListener(new ContactAdapter.ContactAdapterSwipeClickListener() {
            @Override
            public void onSwipeClickListener(int position) {
                mAdapter.remove(mAdapter.getItem(position));
                showToast("删除成功");
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

//        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this,LinearLayoutManager.VERTICAL,10, Color.RED));
        mRecyclerView.addItemDecoration(new DividerDecoration(this, 10));

        swipe = (SuperSwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setHeaderViewBackgroundColor(0xff888888);
        swipe.setHeaderView(createHeaderView());
        swipe.setFooterView(createFooterView());
        swipe.setTargetScrollWithLayout(true);
        swipe.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        swipe.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                        mAdapter.clear();
                        addData();
                    }
                }, 2000);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {
                textView.setText(enable ? "松开刷新" : "下拉刷新");
                imageView.setVisibility(View.VISIBLE);
                imageView.setRotation(enable ? 180 : 0);
            }
        });

        swipe.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        footerImageView.setVisibility(View.VISIBLE);
                        footerProgressBar.setVisibility(View.GONE);
                        swipe.setLoadMore(false);
                        loadMoreData();
                    }
                }, 5000);
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? "松开加载" : "上拉加载");
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);

            }
        });
        addData();

    }

    private void loadMoreData() {
        List<String >  moreData=new ArrayList<>();

        for(int i=0;i<10;i++){
            moreData.add("iiiiiiii "+i);
        }
        mAdapter.addAllItem(moreData);
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(swipe.getContext())
                .inflate(R.layout.layout_footer, null);
        footerProgressBar = (ProgressBar) footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = (ImageView) footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = (TextView) footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(swipe.getContext())
                .inflate(R.layout.layout_head, null);
        progressBar = (ProgressBar) headerView.findViewById(R.id.pb_view);
        textView = (TextView) headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = (ImageView) headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    private void addData() {
        List<String> tempData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tempData.add("i=" + i);
        }
        mAdapter.addAllItem(tempData);

    }

    public void showToast(String value) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }
}
