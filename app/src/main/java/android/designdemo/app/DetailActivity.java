package android.designdemo.app;

import android.designdemo.Cheeses;
import android.designdemo.R;
import android.designdemo.app.base.BaseActivity;
import android.designdemo.view.ListViewAdapter;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author seven
 * @version V1.0
 * @date 15/6/8
 * @description
 */
public class DetailActivity extends BaseActivity {

    private TabLayout mTabs;

    private RecyclerView mListView;

    private SwipeRefreshLayout mRefreshLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            case R.id.action_search:

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        mRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.transparent);
        mRefreshLayout.setBackgroundResource(R.color.transparent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (RecyclerView) findViewById(R.id.listview);
        toolbar.setTitle("Detail");
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle("Detail");

        mTabs = (TabLayout) findViewById(R.id.tabs);
        mTabs.setTabMode(TabLayout.MODE_FIXED);
        setupTabs();

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mListView.setLayoutManager(manager);

        ListViewAdapter adapter = new ListViewAdapter(this, Cheeses.sCheeseStrings);
        mListView.setAdapter(adapter);
    }

    private void setupTabs() {
        for (int i = 1; i < 5; i++)
            mTabs.addTab(mTabs.newTab().setText("Tab " + i));
    }
}
