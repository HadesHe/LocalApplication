package com.hzjytech.hades.gridviewwithheader;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SuperSwipeRefreshLayout swipe;
    private ProgressBar footerProgressBar;
    private ImageView footerImageView;
    private TextView footerTextView;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private HeaderNumberedAdapter adapter;
    private int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        swipe = (SuperSwipeRefreshLayout) findViewById(R.id.swipe);

        setUpSwipe();
        recyclerView.addItemDecoration(new MarginDecoration(this));

        final GridLayoutManager manager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(manager);

        List<CustomEntity> entities = getData();

        adapter = new HeaderNumberedAdapter(this, entities);

        recyclerView.setAdapter(adapter);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isHeader(position) ? manager.getSpanCount() : 1;
            }
        });
    }

    private void setUpSwipe() {
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
                swipe.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                adapter.addRefreshData(getData());
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
                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                swipe.setLoadMore(false);
                adapter.addMoreData(getData(i));
                i++;
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
        View headerView = LayoutInflater.from(swipe.getContext()).inflate(R.layout.layout_head, null);
        progressBar = (ProgressBar) headerView.findViewById(R.id.pb_view);
        textView = (TextView) headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = (ImageView) headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }


    private List<CustomEntity> getData() {
        List<CustomEntity> entityList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            entityList.add(new CustomEntity("0item" + (i + 1)));
        }

        for (int i = 0; i < 11; i++) {
            entityList.add(new CustomEntity("1item" + (i + 1)));
        }

        for (int i = 0; i < 12; i++) {
            entityList.add(new CustomEntity("2item" + (i + 1)));
        }

        return entityList;
    }

    private List<CustomEntity> getData(int j) {
        List<CustomEntity> entityList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            entityList.add(new CustomEntity((j * 3) + "item" + (i + 1)));
        }

        for (int i = 0; i < 12; i++) {
            entityList.add(new CustomEntity((j * 3 + 1) + "item" + (i + 1)));
        }

        for (int i = 0; i < 13; i++) {
            entityList.add(new CustomEntity((j * 3 + 2) + "item" + (i + 1)));
        }

        for (int i = 0; i < 10; i++) {
            entityList.add(new CustomEntity((j * 3 + 3) + "item" + (i + 1)));
        }

        return entityList;
    }
}
