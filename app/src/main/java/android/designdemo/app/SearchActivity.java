package android.designdemo.app;

import android.designdemo.Cheeses;
import android.designdemo.R;
import android.designdemo.app.base.BaseActivity;
import android.designdemo.view.ListViewAdapter;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SearchActivity extends BaseActivity {

    private SearchView mSearchView;

    private RecyclerView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setIconifiedByDefault(false);

        mListView = (RecyclerView) findViewById(R.id.listview);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        linearLayout.setSmoothScrollbarEnabled(true);
//        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mListView.setLayoutManager(manager);
//        mListView.setLayoutManager(linearLayout);

        ListViewAdapter adapter = new ListViewAdapter(this, Cheeses.sCheeseStrings);
        mListView.setAdapter(adapter);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_clear_mtrl_alpha);
//        toolbar.inflateMenu(R.menu.menu_detail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });
    }
}
