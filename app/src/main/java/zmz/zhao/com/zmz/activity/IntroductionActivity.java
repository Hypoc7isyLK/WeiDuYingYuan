package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zmz.zhao.com.zmz.activity.BaseActivity;
/**
 * @作者 啊哈
 * @date 2019/1/25
 * 引导页
 *
 */
public class IntroductionActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.linear)
    LinearLayout linear;
    List<View> views = new ArrayList<View>();
    private int mCurrentIndex = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_introduction;
    }

    @Override
    protected void initView() {

        int a[] = {R.mipmap.coverage1,
                R.mipmap.coverage2,
                R.mipmap.coverage3,
                R.mipmap.coverage4};
        String text[] = {"荡涤你的心灵", "看遍人生百态", "净化你的灵魂", "带你开启一段美好的电影之旅"};

        for (int i = 0; i < a.length - 1; i++) {
            View view = View.inflate(getBaseContext(), R.layout.activity_introduction_item, null);
            ImageView imageView = view.findViewById(R.id.introd_image);
            //添加图片
            imageView.setImageResource(a[i]);
            TextView textViewTop = view.findViewById(R.id.introd_textTop);
            textViewTop.setText("一场电影");
            TextView textViewButtom = view.findViewById(R.id.introd_textBottom);
            //添加文字
            textViewButtom.setText(text[i]);
            views.add(view);
        }
        //获取最后一张布局
        View viewclick = View.inflate(getBaseContext(), R.layout.activity_introduction_item_click, null);

        ImageView imageView = viewclick.findViewById(R.id.introd_image);
        //添加图片
        imageView.setImageResource(a[3]);

        TextView textViewTop = viewclick.findViewById(R.id.introd_textTop);

        TextView textViewButtom = viewclick.findViewById(R.id.introd_textBottom);
        //添加文字
        textViewTop.setText("八维移动通信学院作品");

        textViewButtom.setText(text[3]);
        //将布局添加到集合
        views.add(viewclick);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {

                View view1 = views.get(position);

                container.addView(view1);

                return view1;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });

        for (int i = 0; i < views.size(); i++) {

            ImageView dot = new ImageView(this);
            if (i == mCurrentIndex) {
                dot.setImageResource(R.drawable.rbtn_t);//设置当前页的圆点
            } else {
                dot.setImageResource(R.drawable.rbtn_f);//其余页的圆点
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                    .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.leftMargin = 10;//设置圆点边距
            }
            dot.setLayoutParams(params);
            linear.addView(dot);//将圆点添加到容器中
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mCurrentIndex = i;
                for (int position = 0; position < linear.getChildCount(); position++) {
                    ImageView imageView = (ImageView) linear.getChildAt(position);
                    if (position == i) {
                        imageView.setImageResource(R.drawable.rbtn_t);
                    } else {
                        imageView.setImageResource(R.drawable.rbtn_f);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroductionActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        saveData();

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("movie", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("flag", true);

        editor.commit();
    }

    @Override
    protected void destoryData() {

    }
}
