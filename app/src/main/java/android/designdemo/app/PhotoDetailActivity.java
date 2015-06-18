package android.designdemo.app;

import android.content.Intent;
import android.designdemo.Cheeses;
import android.designdemo.R;
import android.designdemo.app.base.BaseActivity;
import android.designdemo.view.ListViewAdapter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author seven
 * @version V1.0
 * @date 15/6/8
 * @description
 */
public class PhotoDetailActivity extends BaseActivity {

    private RecyclerView mListView;

    private SwipeRefreshLayout mRefreshLayout;

    private ImageView mIvImage;

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
        setContentView(R.layout.activity_photo_detail);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        mRefreshLayout.setEnabled(false);

        mIvImage = (ImageView) findViewById(R.id.ivImage);
        Intent intent = getIntent();
        if (null != intent) {
            int res = intent.getIntExtra("image", -1);
            if (res > 0) {
                setImage(res);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (RecyclerView) findViewById(R.id.listview);

        ViewCompat.setTransitionName(mIvImage, "image");
        ViewCompat.setTransitionName(mListView, "item");

        toolbar.setTitle("Detail");
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle("Detail");


        LinearLayoutManager manager = new LinearLayoutManager(this);
        mListView.setLayoutManager(manager);

        ListViewAdapter adapter = new ListViewAdapter(this, Cheeses.sCheeseStrings);
        mListView.setAdapter(adapter);
    }

    private void setImage(int res) {
        Drawable d = getDrawable(res);
        if (null == d) {
            return;
        }
        ViewGroup.LayoutParams params = mIvImage.getLayoutParams();
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        params.height = metrics.widthPixels * d.getIntrinsicHeight() / d.getIntrinsicWidth();
        mIvImage.setLayoutParams(params);
        mIvImage.setImageDrawable(d);
    }
}
