package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
            SharedPreferences sharedPreferences = getSharedPreferences("movie", MODE_PRIVATE);
            time--;
            if (time > 0) {

                handler.sendEmptyMessageDelayed(0, 1000);

            } else {

                boolean flag = sharedPreferences.getBoolean("flag", false);

                if (flag){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {


        if (time != 0) {
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    }

    @Override
    protected void destoryData() {
        handler.removeCallbacks(null);
    }
}
