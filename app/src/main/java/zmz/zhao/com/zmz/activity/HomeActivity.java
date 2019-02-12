package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import zmz.zhao.com.zmz.fragment.HomeFragment;
import zmz.zhao.com.zmz.fragment.MineFragment;
import zmz.zhao.com.zmz.fragment.MovieFragment;
import zmz.zhao.com.zmz.view.BackNum;

public class HomeActivity extends BaseActivity implements BackNum {


    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.home_radio)
    RadioGroup home_radio;

    HomeFragment homeFragment;
    MovieFragment movieFragment;
    MineFragment mineFragment;
    private FragmentManager manager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        homeFragment = new HomeFragment();
        movieFragment = new MovieFragment();
        mineFragment = new MineFragment();

        manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame, homeFragment);
        transaction.commit();

        home_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    //主页
                    case R.id.homePage:
                        AlphaAnimation alphaAnimation = new AlphaAnimation(2.0f, 0.0f);

                        FragmentTransaction transaction1 = manager.beginTransaction();
                        transaction1.replace(R.id.frame,homeFragment);
                        transaction1.commit();
                        break;
                    //影院
                    case R.id.homeMovie:
                        FragmentTransaction transaction2 = manager.beginTransaction();
                        transaction2.replace(R.id.frame,movieFragment);
                        transaction2.commit();
                        break;
                    //我的
                    case R.id.mine:
                        FragmentTransaction transaction3 = manager.beginTransaction();
                        transaction3.replace(R.id.frame,mineFragment);
                        transaction3.commit();
                        break;
                }
            }
        });

    }

    @Override
    protected void destoryData() {

    }

    @Override
    public void getNum(int id) {
        Intent intent = new Intent(HomeActivity.this, InsideDetailsActivity.class);
        intent.putExtra("id",id+"");
        startActivity(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("主页Activity");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("主页Activity");
        MobclickAgent.onPause(this);
    }
    //退出时的时间
    private long mExitTime;
    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(HomeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {

            finish();
            System.exit(0);
        }
    }

}
