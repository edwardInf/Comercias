package pe.acex.comercias.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.util.TypedValue;

public class Adapter {

    public int DpToPx(Activity activity, int dp){
        Resources r  = activity.getResources();
        int px    = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;

    }
}
