package android.designdemo.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.designdemo.Images;
import android.designdemo.R;
import android.designdemo.app.PhotoDetailActivity;
import android.designdemo.util.SDKUtils;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author seven
 * @version V1.0
 * @date 15/6/8
 * @description
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private String[] mData;

    private Context mContext;

    private SparseArrayCompat<Boolean> mPositionArray;

    private int mPrePosition;

    private int screenWidth, screenHeight;

    public ContactAdapter(Context context, String[] data) {
        mContext = context;
        mData = data;
        mPositionArray = new SparseArrayCompat<>();
        mPrePosition = -1;
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_contact, null);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    private boolean isAnimed(int position) {
        if (null != mPositionArray) {
            return mPositionArray.get(position, false);
        }
        return false;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.setData(Images.get(i), mData[i], "Index " + (i + 1));
//        if (i > mPrePosition && !isAnimed(i)) {
//            // start animation
//            View v = viewHolder.itemView;
//            Animation a = AnimationUtils.loadAnimation(mContext, R.anim.abc_slide_in_bottom);
//            System.out.println("Show animation");
//            v.startAnimation(a);
//            mPrePosition = i;
//            mPositionArray.put(i, true);
//        }
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView mIvThumb;

        private TextView mTvTitle, mTvSubTtle;

        public ViewHolder(View itemView) {
            super(itemView);
            mIvThumb = (CircleImageView) itemView.findViewById(R.id.ivThumb);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvSubTtle = (TextView) itemView.findViewById(R.id.tvSubTitle);
            itemView.setOnClickListener(LISTENER);
        }

        public void setData(int imgres, String title, String subtitle) {
            mIvThumb.setImageResource(imgres);
            mTvTitle.setText(title);
            mTvSubTtle.setText(subtitle);
            itemView.setTag(imgres);
        }
    }

    private static View.OnClickListener LISTENER = new View.OnClickListener() {

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            if (null != context) {
                int image = (int) v.getTag();
                Intent intent = new Intent(context, PhotoDetailActivity.class);
                intent.putExtra("image", image);
                if (SDKUtils.hasLollipop() && context instanceof Activity) {
                    Activity activity = (Activity) context;
                    View imageView = v.findViewById(R.id.ivThumb);
                    Pair<View, String> pair1 = Pair.create(imageView, "image");
                    Pair<View, String> pair2 = Pair.create(v, "item");
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair1, pair2);
                    context.startActivity(intent, options.toBundle());
                    return;
                }
                context.startActivity(intent);
            }

        }
    };
}