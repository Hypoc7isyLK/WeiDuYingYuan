package zmz.zhao.com.zmz.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import zmz.zhao.com.zmz.util.WechatUtil;

/**
 * date:2019/1/22
 * author:李阔(淡意衬优柔)
 * function:
 */
public class MyApplication extends Application {

    /** 主线程ID */
    private static int mMainThreadId = -1;
    /** 主线程ID */
    private static Thread mMainThread;
    /** 主线程Handler */
    private static Handler mMainThreadHandler;
    /** 主线程Looper */
    private static Looper mMainLooper;

    /**
     * context 全局唯一的上下文
     */
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "8448d533e7", true);
        context=this;
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        WechatUtil.init(this);

        Fresco.initialize(this);
        //微信
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        msgApi.registerApp("wxb3852e6a6b7d9516");

    }

    public static Context getContext() {
        return context;
    }

    /** 获取主线程ID */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /** 获取主线程 */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /** 获取主线程的handler */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /** 获取主线程的looper */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }
}
