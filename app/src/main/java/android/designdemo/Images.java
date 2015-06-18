package android.designdemo;

/**
 * @author seven
 * @version V1.0
 * @date 15/6/12
 * @description
 */
public final class Images {

    private static final int[] IMAGES = {R.drawable.test_img_1, R.drawable.test_img_2, R.drawable.test_img_3, R.drawable.test_img_4, R.drawable.test_img_5};

    public static int get(int position) {
        return IMAGES[position % IMAGES.length];
    }
}
