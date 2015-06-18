package android.designdemo.view;

import android.content.Context;
import android.designdemo.R;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static android.support.v7.widget.RecyclerView.ItemDecoration;
import static android.support.v7.widget.RecyclerView.State;

public class SimpleDividerItemDecoration extends ItemDecoration {
//    private Drawable mDivider;

    private int dividerHeight = 1;

    private Paint mLinePaint;

    private int mLeft;

    public SimpleDividerItemDecoration(Context context) {
//        mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.divider_height);
        mLeft = context.getResources().getDimensionPixelSize(R.dimen.item_content_padding_left);
        mLinePaint = new Paint();
        mLinePaint.setStrokeWidth(dividerHeight);
        mLinePaint.setColor(Color.parseColor("#1e000000"));
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, State state) {
        int left = mLeft;
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + dividerHeight;
            c.drawLine(left, top, right, top, mLinePaint);

//            mDivider.setBounds(left, top, right, bottom);
//            mDivider.draw(c);
        }
    }
}
