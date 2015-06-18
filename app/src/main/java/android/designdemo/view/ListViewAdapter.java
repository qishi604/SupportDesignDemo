package android.designdemo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.designdemo.R;
import android.designdemo.app.DetailActivity;
import android.os.Build;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author seven
 * @version V1.0
 * @date 15/6/8
 * @description
 */
public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private String[] mData;

    private Context mContext;

    private SparseArrayCompat<Boolean> mPositionArray;

    private int mPrePosition;

    private int screenWidth, screenHeight;

    public ListViewAdapter(Context context, String[] data) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, null);

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
        viewHolder.setData(mData[i], "Index " + (i + 1));
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvTitle, mTvSubTtle;

        public ViewHolder(View itemView) {
            super(itemView);
            System.out.println("Create View");
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvSubTtle = (TextView) itemView.findViewById(R.id.tvSubTitle);
            itemView.setOnClickListener(LISTENER);
        }

        public void setData(String title, String subtitle) {
            mTvTitle.setText(title);
            mTvSubTtle.setText(subtitle);
        }
    }

    private static View.OnClickListener LISTENER = new View.OnClickListener() {

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            if (null != context) {
                context.startActivity(new Intent(context, DetailActivity.class));
            }

        }
    };
}