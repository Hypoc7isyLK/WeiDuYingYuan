package zmz.zhao.com.zmz.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.greendao.gen.DaoMaster;
import com.greendao.gen.UserInfoDao;

import java.util.List;

import butterknife.ButterKnife;
import zmz.zhao.com.zmz.bean.dao.UserInfo;


public abstract class BaseActivity extends AppCompatActivity {

    public UserInfo USER_INFO;
    public UserInfoDao userInfoDao;

    private static BaseActivity mForegroundActivity = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();

        List<UserInfo> userInfo = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();

        if (userInfo != null && userInfo.size()>0) {
            USER_INFO = userInfo.get(0);
        }


        setContentView(getLayoutId());

        ButterKnife.bind(this);

        initView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }
    /**
     * 设置layoutId
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 清除数据
     */
    protected abstract void destoryData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destoryData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mForegroundActivity = this;
    }

    public static BaseActivity getForegroundActivity() {
        return mForegroundActivity;
    }
}
