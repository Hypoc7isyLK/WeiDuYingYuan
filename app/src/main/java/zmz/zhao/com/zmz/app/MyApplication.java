package zmz.zhao.com.zmz.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.facebook.drawee.backends.pipeline.Fresco;
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

   /* // APP_ID 替换为你的应用从官方网站申请到的合法appID
    public static final String APP_ID = "wxb3852e6a6b7d9516";
    // IWXAPI 是第三方app和微信通信的openApi接口
    public static IWXAPI api;*/



    @Override
    public void onCreate() {
        super.onCreate();
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
        /*registToWX();*/
    }

    /**
     * 设置greenDAO
     */


    /*private void registToWX() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
    }*/
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
