package zmz.zhao.com.zmz.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import zmz.zhao.com.zmz.util.WechatUtil;
import com.greendao.gen.UserDaoDao;

import java.util.List;

import zmz.zhao.com.zmz.bean.dao.UserDao;

/**
 * date:2019/1/22
 * author:李阔(淡意衬优柔)
 * function:
 */
public class MyApplication extends Application {
    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    public static final String APP_ID = "wxb3852e6a6b7d9516";
    // IWXAPI 是第三方app和微信通信的openApi接口
    public static IWXAPI api;



    @Override
    public void onCreate() {
        super.onCreate();
        WechatUtil.init(this);

        Fresco.initialize(this);
        //微信
        registToWX();
    }

    /**
     * 设置greenDAO
     */


    private void registToWX() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
    }
}
