package org.zeniafrik.ui.market;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zeniafrik.R;
import org.zeniafrik.R.layout;
import org.zeniafrik.api.meta;
import org.zeniafrik.di.Injectable;
import org.zeniafrik.factory.ViewModelFactory;
import org.zeniafrik.helper.ViewLifecycleFragment;
import org.zeniafrik.models.Feed;
import org.zeniafrik.ui.market.ViewModels.FeedFragmentViewModel;
import org.zeniafrik.ui.market.adapters.FeedListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static java.util.Objects.requireNonNull;

/**
 * Created by BraDev ${LOCALE} on 5/2/2018.
 */
public class FeedFragment extends ViewLifecycleFragment implements Injectable, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.feedList)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.error_view)
    View error_view;

    @BindView(R.id.empty)
    View empty_view;

    @Inject
    ViewModelFactory factory;
    @Inject
    FeedFragmentViewModel viewModel;

    private int current_page = 1, last_page = 1;
    private Unbinder unbinder;
    private FeedListAdapter adapter;
    private boolean loading;

    @OnClick(R.id.retry_btn)
    public void retry() {
        onRefresh();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new FeedListAdapter();

        viewModel = ViewModelProviders.of(FeedFragment.this, factory).get(FeedFragmentViewModel.class);
        viewModel.getFeeds().observe(getViewLifecycleOwner(), feed_response -> {
            switch (requireNonNull(feed_response).status) {
                case SUCCESS:
                    loaded_ok(requireNonNull(feed_response.data).getData(), feed_response.data.getMeta());
                    break;
                case ERROR:
                    error_occurred();
                    break;
                case LOADING:
                    swipeRefreshLayout.setRefreshing(true);
            }
        });


        swipeRefreshLayout.setOnRefreshListener(FeedFragment.this);
        recyclerView.setAdapter(adapter);

        Context context = requireNonNull(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(context, R.dimen.linear_minimal_outline));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(-1) && (current_page <= last_page) && !loading) requestFeeds();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && adapter.getItemCount() < 1) requestFeeds();
    }

    private void error_occurred() {
        swipeRefreshLayout.setRefreshing(false);
        loading = false;
        if (!error_view.isShown()) error_view.setVisibility(View.VISIBLE);
        if (empty_view.isShown()) empty_view.setVisibility(View.GONE);
        if (recyclerView.isShown()) recyclerView.setVisibility(View.GONE);
    }

    private void loaded_ok(ArrayList<Feed> feeds, meta meta) {
        if (error_view.isShown()) error_view.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        loading = false;
        if (!feeds.isEmpty()) {
            if(current_page == 1) adapter.addFeeds(feeds);
            else adapter.add_moreFeeds(feeds);
            extract_meta_info(meta);
            refreshView();
        } else renderEmpty();

    }

    private void extract_meta_info(meta meta) {
        if(meta.current_page == 1) last_page = meta.last_page;
        if (meta.current_page <= last_page) current_page++;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed() && isVisibleToUser) onResume();
    }
    private void renderEmpty(){
        if (!empty_view.isShown()) empty_view.setVisibility(View.VISIBLE);
        if (recyclerView.isShown()) recyclerView.setVisibility(View.GONE);
    }
    private void refreshView(){
        if (!recyclerView.isShown()) recyclerView.setVisibility(View.VISIBLE);
        if (empty_view.isShown()) empty_view.setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout.feed_fragment, container, false);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
        adapter = null;
    }

    @Override
    public void onRefresh() {
        current_page = 1;
        last_page = 1;
        requestFeeds();
    }

    private void requestFeeds(){
        swipeRefreshLayout.setRefreshing(true);
        loading = true;
        viewModel.setPage(current_page);
    }

}


class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    public ItemOffsetDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mItemOffset);
    }
}
