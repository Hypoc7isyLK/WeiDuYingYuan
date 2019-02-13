package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
/**
 * @作者 啊哈
 * @date 2019/1/25
 * 启动页
 */
public class MainActivity extends AppCompatActivity {

    private int time = 3;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SharedPreferences sharedPreferences = getSharedPreferences("movie", MODE_PRIVATE);
            time--;
            if (time > 0) {

                handler.sendEmptyMessageDelayed(0, 1000);

            } else {

                boolean flag = sharedPreferences.getBoolean("flag", false);

                if (flag){
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                    return;
                }
                startActivity(new Intent(MainActivity.this, IntroductionActivity.class));
                finish();
            }
        }
    };
    @BindView(R.id.main)
    RelativeLayout main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (time != 0) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(3500);
            main.startAnimation(alphaAnimation);
            handler.sendEmptyMessageDelayed(0, 1000);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("倒计时页面");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("倒计时页面");
        MobclickAgent.onPause(this);
    }
}
