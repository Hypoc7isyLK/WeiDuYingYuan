package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.bw.movie.R;

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
    public void getLogin(String isLogin) {
        if (isLogin.equals("1")){
            Intent intent = new Intent(this, LoginActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.putExtra("isLogin",isLogin);

            startActivity(intent);
        }else return;
    }
}
