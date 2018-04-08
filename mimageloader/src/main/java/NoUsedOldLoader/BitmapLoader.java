package NoUsedOldLoader;

import android.view.View;

/**
 * Created by mac on 2018/4/8.
 */

public class BitmapLoader extends mLoader {
    @Override
    protected boolean checkSource(Object o) {
        return false;
    }

    @Override
    public void load(Object o, View imageView, Object tag) {

    }
}
