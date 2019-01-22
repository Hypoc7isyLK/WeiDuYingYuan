package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import butterknife.BindView;
import zmz.zhao.com.zmz.R;

public class MainActivity extends BaseActivity {
    private int time = 3;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            if (time > 0) {
                handler.sendEmptyMessageDelayed(0, 1000);

            } else {
                startActivity(new Intent(MainActivity.this, IntroductionActivity.class));
                finish();
            }
        }
    };
    @BindView(R.id.main)
    RelativeLayout main;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        if (time != 0) {
            handler.sendEmptyMessageDelayed(0, 1000);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(3500);
            main.startAnimation(alphaAnimation);
        }
    }

    @Override
    protected void destoryData() {
        handler.removeCallbacks(null);
    }
}
