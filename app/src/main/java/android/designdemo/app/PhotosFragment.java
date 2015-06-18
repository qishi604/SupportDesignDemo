package android.designdemo.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.designdemo.Images;
import android.designdemo.R;
import android.designdemo.app.base.BaseFragment;
import android.designdemo.util.SDKUtils;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 * @author seven
 * @version V1.0
 * @date 15/6/5
 * @description
 */
public class PhotosFragment extends BaseFragment {

    private RecyclerView mListView;
    private SwipeRefreshLayout mRefreshLayout;

    PhotoAdapter mAdapter;

    public static PhotosFragment newInstance() {

        PhotosFragment fragment = new PhotosFragment();
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
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mListView = findView(R.id.listview);
        mListView.addItemDecoration(new TwoItemDecoration((int) (4 * metrics.density)));
        mRefreshLayout = findView(R.id.refreshLayout);


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
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        mListView.setLayoutManager(manager);
//        mListView.setLayoutManager(linearLayout);

        mAdapter = new PhotoAdapter(getActivity());
        mListView.setAdapter(mAdapter);

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
                        mAdapter.clearAnim();

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

    private static class TwoItemDecoration extends RecyclerView.ItemDecoration {

        private static final int DEFAULT_ITEM_INSET = 4;

        private int mInset = DEFAULT_ITEM_INSET;

        public TwoItemDecoration() {
        }


        public TwoItemDecoration(int inset) {
            mInset = inset;
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int size = mInset;
            int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            int top = position <= 1 ? size : 0;
            if ((position & 1) == 1) {
                outRect.set(0, top, size, size);
            } else {
                outRect.set(size, top, size, size);
            }
        }
    }

    private static class Holder extends RecyclerView.ViewHolder {

        private ImageView ivImage;

        private View vClick;

        public Holder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            vClick = itemView.findViewById(R.id.vClick);
        }

        public void setData(int image) {
            ivImage.setImageResource(image);
            vClick.setTag(image);
            vClick.setOnClickListener(ItemClickListener);
        }
    }

    private static View.OnClickListener ItemClickListener = new View.OnClickListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            if (null != context && context instanceof AppCompatActivity) {
                int imgRes = (int) v.getTag();
                Activity activity = (Activity) context;
                Intent intent = new Intent(activity, PhotoDetailActivity.class);
                intent.putExtra("image", imgRes);
                if (SDKUtils.hasLollipop()) {
                    ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, v, "image");
                    activity.startActivity(intent, option.toBundle());
                } else {
                    activity.startActivity(intent);
                }
            }
        }
    };

    private static class PhotoAdapter extends RecyclerView.Adapter<Holder> {

        private int height;

        private int mPrePosition;

        private SparseArrayCompat<Boolean> mPositionArray;

        private Context mContext;

        public PhotoAdapter(Context context) {
            mContext = context;
            mPositionArray = new SparseArrayCompat<>();
            mPrePosition = -1;
            height = context.getResources().getDisplayMetrics().heightPixels;
        }

        private boolean isAnimed(int position) {
            if (null != mPositionArray) {
                return mPositionArray.get(position, false);
            }
            return false;
        }

        public void clearAnim() {
            mPositionArray.clear();
            mPrePosition = -1;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_photos, null);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int i) {
            holder.setData(Images.get(i));
            if (i > mPrePosition && !isAnimed(i)) {
                mPrePosition = i;
                // start animation
                View v = holder.itemView;

                ViewCompat.setTranslationX(v, 0f);
                ViewCompat.setTranslationY(v, height);
//                ViewCompat.setRotationX(v, 45);
//                ViewCompat.setScaleX(v, 0.7f);
//                ViewCompat.setScaleY(v, 0.55f);

                ViewPropertyAnimatorCompat animatorCompat = ViewCompat.animate(v)
//                        .rotationX(0f).rotationY(0f)
                        .translationX(0).translationY(0)
                        .setDuration(400)
//                        .scaleX(1.0f).scaleY(1.0f)
                        .setInterpolator(new DecelerateInterpolator());
                animatorCompat.setStartDelay(0).start();

                mPositionArray.put(i, true);
            }
        }

        @Override
        public int getItemCount() {
            return 200;
        }
    }

}
