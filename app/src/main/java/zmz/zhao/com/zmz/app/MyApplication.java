package zmz.zhao.com.zmz.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;
import com.greendao.gen.UserDaoDao;

import java.util.List;

import zmz.zhao.com.zmz.bean.dao.UserDao;

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
