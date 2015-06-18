package android.designdemo.app.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author seven
 * @version V1.0
 * @date 15/6/11
 * @description
 */
public abstract class BaseFragment extends Fragment {

    protected View mContentView;

    protected static void snack(View view, @NonNull CharSequence msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(getContentViewRes(), container, false);
        findViews();
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize(savedInstanceState);
    }

    @NonNull
    @LayoutRes
    protected abstract int getContentViewRes();

    protected <T>T findView(@IdRes int id) {
        if (null == mContentView) {
            return null;
        }
        return (T) mContentView.findViewById(id);
    }

    protected abstract void findViews();

    protected void initialize(@Nullable Bundle savedInstanceState) {

    }
}
