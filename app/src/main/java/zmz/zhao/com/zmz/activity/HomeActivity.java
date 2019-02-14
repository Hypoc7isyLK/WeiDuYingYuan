package zmz.zhao.com.zmz.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.homePage)
    RadioButton homePage;
    @BindView(R.id.homeMovie)
    RadioButton homeMovie;
    @BindView(R.id.mine)
    RadioButton mine;
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


                        FragmentTransaction transaction1 = manager.beginTransaction();
                        transaction1.replace(R.id.frame, homeFragment);
                        AnimatorSet set = new AnimatorSet();
                        ObjectAnimator o1 = ObjectAnimator.ofFloat(R.id.homePage, "scaleX", 1.17f);
                        ObjectAnimator o2 = ObjectAnimator.ofFloat(R.id.homeMovie, "scaleX", 1.0f);
                        ObjectAnimator o3 = ObjectAnimator.ofFloat(R.id.mine, "scaleX", 1.0f);

                        ObjectAnimator o4 = ObjectAnimator.ofFloat(R.id.homePage, "scaleY", 1.17f);
                        ObjectAnimator o5 = ObjectAnimator.ofFloat(R.id.homeMovie, "scaleY", 1.0f);
                        ObjectAnimator o6 = ObjectAnimator.ofFloat(R.id.mine, "scaleY", 1.0f);
                        //存入集合
                        set.playTogether(o1,o2,o3,o4,o5,o6);
                        //开始执行
                        set.start();
                        transaction1.commit();

                        break;
                    //影院
                    case R.id.homeMovie:
                        FragmentTransaction transaction2 = manager.beginTransaction();
                        transaction2.replace(R.id.frame, movieFragment);
                        AnimatorSet set1 = new AnimatorSet();
                        ObjectAnimator o11 = ObjectAnimator.ofFloat(R.id.homePage, "scaleX", 1.0f);
                        ObjectAnimator o22 = ObjectAnimator.ofFloat(R.id.homeMovie, "scaleX", 1.17f);
                        ObjectAnimator o33 = ObjectAnimator.ofFloat(R.id.mine, "scaleX", 1.0f);

                        ObjectAnimator o44 = ObjectAnimator.ofFloat(R.id.homePage, "scaleY", 1.0f);
                        ObjectAnimator o55 = ObjectAnimator.ofFloat(R.id.homeMovie, "scaleY", 1.17f);
                        ObjectAnimator o66 = ObjectAnimator.ofFloat(R.id.mine, "scaleY", 1.0f);
                        //存入集合
                        set1.playTogether(o11,o22,o33,o44,o55,o66);
                        //开始执行
                        set1.start();
                        transaction2.commit();
                        break;
                    //我的
                    case R.id.mine:
                        FragmentTransaction transaction3 = manager.beginTransaction();
                        transaction3.replace(R.id.frame, mineFragment);
                        AnimatorSet set2 = new AnimatorSet();
                        ObjectAnimator o111 = ObjectAnimator.ofFloat(R.id.homePage, "scaleX", 1.0f);
                        ObjectAnimator o222 = ObjectAnimator.ofFloat(R.id.homeMovie, "scaleX", 1.0f);
                        ObjectAnimator o333 = ObjectAnimator.ofFloat(R.id.mine, "scaleX", 1.17f);

                        ObjectAnimator o444 = ObjectAnimator.ofFloat(R.id.homePage, "scaleY", 1.0f);
                        ObjectAnimator o555 = ObjectAnimator.ofFloat(R.id.homeMovie, "scaleY", 1.0f);
                        ObjectAnimator o666 = ObjectAnimator.ofFloat(R.id.mine, "scaleY", 1.17f);
                        //存入集合
                        set2.playTogether(o111,o222,o333,o444,o555,o666);
                        //开始执行
                        set2.start();
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
        intent.putExtra("id", id + "");
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
