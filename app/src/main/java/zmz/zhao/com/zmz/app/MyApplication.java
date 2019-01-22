package zmz.zhao.com.zmz.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * date:2019/1/22
 * author:李阔(淡意衬优柔)
 * function:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
