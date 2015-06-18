package android.designdemo.app;

import android.content.Context;
import android.content.Intent;
import android.designdemo.Cheeses;
import android.designdemo.R;
import android.designdemo.app.base.BaseFragment;
import android.designdemo.view.ListViewAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author seven
 * @version V1.0
 * @date 15/6/5
 * @description
 */
public class RecycleViewFragment extends BaseFragment {

    private RecyclerView mListView;
    private SwipeRefreshLayout mRefreshLayout;

    public static RecycleViewFragment newInstance() {

        RecycleViewFragment fragment = new RecycleViewFragment();
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            mRefreshLayout.setRefreshing(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_recycle;
    }

    @Override
    protected void findViews() {
        mListView = findView(R.id.listview);
        mRefreshLayout = findView(R.id.refreshLayout);

        DisplayMetrics metrics = getResources().getDisplayMetrics();

//        mRefreshLayout.setBackgroundResource(R.drawable.transparent);
//        mRefreshLayout.setProgressViewEndTarget(true, 200);
        mRefreshLayout.setProgressViewOffset(true, -100, (int) (64 * metrics.density));
//        mRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.black);
//        mRefreshLayout.setProgressBackgroundColorSchemeColor(Color.TRANSPARENT);
        mRefreshLayout.setColorSchemeResources(R.color.pink_500);
    }

    @Override
    protected void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        setHasOptionsMenu(true);
        LinearLayoutManager linearLayout = new ListViewManager(getActivity());
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        linearLayout.setSmoothScrollbarEnabled(true);
//        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mListView.setLayoutManager(manager);
//        mListView.setLayoutManager(linearLayout);

        ListViewAdapter adapter = new ListViewAdapter(getActivity(), Cheeses.sCheeseStrings);
        mListView.setAdapter(adapter);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                snack(mRefreshLayout, "Refresh...");
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                mRefreshLayout.setRefreshing(false);
                                snack(mRefreshLayout, "Refresh complete...");
                            }
                        });
                    }
                }.start();
            }
        });
    }

    private static class ListViewManager extends LinearLayoutManager {

        public ListViewManager(Context context) {
            super(context);
            setOrientation(VERTICAL);
        }

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {

            return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private static View.OnClickListener LISTENER = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
//            final Snackbar s = Snackbar.make(v, "Hello", Snackbar.LENGTH_LONG);
//            s.setDuration(10000);
//            s.setAction("OK", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (null != s) {
//                        s.dismiss();
//                    }
//                }
//            });
//            s.show();

            Context context = v.getContext();
            if (null != context) {
                context.startActivity(new Intent(context, DetailActivity.class));
            }

        }
    };
}
