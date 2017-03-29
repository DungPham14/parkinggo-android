package parkinggo.com.util.font;

import android.content.Context;

import parkinggo.com.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class FontUtils {
    private FontUtils() {
        throw new AssertionError("No instance allowed");
    }

    public static void initFont(Context context) {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(context.getString(R.string.kanitRegular))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
