package android.designdemo.app;

import android.content.Context;
import android.designdemo.Cheeses;
import android.designdemo.R;
import android.designdemo.app.base.BaseFragment;
import android.designdemo.view.ContactAdapter;
import android.designdemo.view.SimpleDividerItemDecoration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.ViewGroup;

/**
 * @author seven
 * @version V1.0
 * @date 15/6/5
 * @description
 */
public class ContactFragment extends BaseFragment {

    private RecyclerView mListView;
    private SwipeRefreshLayout mRefreshLayout;

    public static ContactFragment newInstance() {

        ContactFragment fragment = new ContactFragment();
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
        mRefreshLayout.setProgressViewEndTarget(true, (int) (64 * metrics.density));
        mRefreshLayout.setColorSchemeResources(R.color.pink_500);
    }

    @Override
    protected void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        setHasOptionsMenu(true);
        mListView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        LinearLayoutManager linearLayout = new ListViewManager(getActivity());
        mListView.setLayoutManager(linearLayout);

        ContactAdapter adapter = new ContactAdapter(getActivity(), Cheeses.sCheeseStrings);
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

        private Context mContext;

        public ListViewManager(Context context) {
            super(context);
            mContext = context;
            setOrientation(VERTICAL);
        }

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            int h = mContext.getResources().getDimensionPixelSize(R.dimen.two_lines);
            return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h);
        }
    }
}
