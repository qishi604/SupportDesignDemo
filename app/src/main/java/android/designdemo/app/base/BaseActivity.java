package android.designdemo.app.base;

import android.annotation.SuppressLint;
import android.designdemo.util.SDKUtils;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

/**
 * @author seven
 * @version V1.0
 * @date 15/6/10
 * @description
 */
public class BaseActivity extends AppCompatActivity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (SDKUtils.hasLollipop()) {
            Window window = getWindow();
            if (null != window) {
                window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//                window.setEnterTransition(new Slide(Gravity.BOTTOM));
//                window.setExitTransition(new Fade());
//                window.setReturnTransition(null);
//                TransitionSet set = new TransitionSet();
//                set.addTransition(new ChangeImageTransform());
//                set.addTransition(new ChangeBounds());
//                window.setSharedElementEnterTransition(set);
//                window.setReenterTransition(new Fade());
//                window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        super.onCreate(savedInstanceState);
    }

    protected static void snack(View view, @NonNull CharSequence msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }
}
