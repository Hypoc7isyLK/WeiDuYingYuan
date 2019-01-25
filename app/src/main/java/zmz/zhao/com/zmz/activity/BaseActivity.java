package zmz.zhao.com.zmz.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.greendao.gen.DaoMaster;
import com.greendao.gen.UserDaoDao;

import java.util.List;

import butterknife.ButterKnife;
import zmz.zhao.com.zmz.bean.dao.UserDao;


public abstract class BaseActivity extends AppCompatActivity {

    public UserDao USERDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserDaoDao userDaoDao = DaoMaster.newDevSession(this, UserDaoDao.TABLENAME).getUserDaoDao();

        List<UserDao> userDaoList = userDaoDao.queryBuilder().where(UserDaoDao.Properties.Status.eq(1)).list();

        if (userDaoList != null && userDaoList.size()>0) {
            USERDAO = userDaoList.get(0);
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
}
