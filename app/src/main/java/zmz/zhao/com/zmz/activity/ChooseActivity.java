package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;
import com.qfdqc.views.seattable.SeatTable;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zmz.zhao.com.zmz.dialog.ChooseDialog;
import zmz.zhao.com.zmz.view.Marquee;

public class ChooseActivity extends BaseActivity {


    @BindView(R.id.choose_cinema_settable)
    SeatTable chooseCinemaSettable;
    @BindView(R.id.choose_movie_price)
    TextView chooseMoviePrice;
    @BindView(R.id.wechat_pay)
    Button wechatPay;
    @BindView(R.id.wechat_cancel)
    Button wechatCancel;
    @BindView(R.id.choose_cinema_name)
    TextView chooseCinemaName;
    @BindView(R.id.choose_cinema_address)
    TextView chooseCinemaAddress;
    @BindView(R.id.choose_movie_name)
    TextView chooseMovieName;
    @BindView(R.id.choose_cinema_marquee)
    Marquee chooseCinemaMarquee;
    private Intent mIntent;
    private String mId;
    private String mmPrice;
    private String mScreeningHall;
    private String mAddress;
    private String mName;
    private String mCinemaname;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mIntent = getIntent();
        mId = mIntent.getStringExtra("id");
        mmPrice = mIntent.getStringExtra("price");
        mScreeningHall = mIntent.getStringExtra("screeningHall");
        mAddress = mIntent.getStringExtra("address");
        mName = mIntent.getStringExtra("name");
        mCinemaname = mIntent.getStringExtra("cinemaname");

        chooseCinemaName.setText(mCinemaname);
        chooseCinemaAddress.setText(mAddress);
        chooseMovieName.setText(mName);


        //初始化影院选座页面对应的影院以及影片信息
        initChooseMessage();

        //初始化电影选座
        initSetTable();



        /*SpannableString spannableString = changTVsize("53.9");
        chooseMoviePrice.setText(spannableString);*/
    }

    private void initSetTable() {
        chooseCinemaSettable.setSeatChecker(new SeatTable.SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 4 && column == 5) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                changePriceWithSelected();
            }

            @Override
            public void unCheck(int row, int column) {
                changePriceWithUnSelected();
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }
        });
        chooseCinemaSettable.setData(8, 15);

    }

    private void initChooseMessage() {

        String mPrice = mmPrice;
        mPriceWithCalculate = new BigDecimal(mPrice);
    }

    private BigDecimal mPriceWithCalculate;
    private int selectedTableCount = 0;

    //假的
    //实际需要保存位置信息并给服务端
    //选中座位时价格联动
    private void changePriceWithSelected() {
        selectedTableCount++;
        String currentPrice = mPriceWithCalculate.multiply(new BigDecimal(String.valueOf(selectedTableCount))).toString();
        Log.e("lk", "currentprice" + currentPrice);
        SpannableString spannableString = changTVsize(currentPrice);
        chooseMoviePrice.setText(spannableString);
        //计算机：处理浮点数是不精确的1.2 - 02   = 1   =》    0.9999999999
    }

    //取消选座时价格联动
    private void changePriceWithUnSelected() {
        selectedTableCount--;
        String currentPrice = mPriceWithCalculate.multiply(new BigDecimal(String.valueOf(selectedTableCount))).toString();
        SpannableString spannableString = changTVsize(currentPrice);
        chooseMoviePrice.setText(spannableString);
    }


    @Override
    protected void destoryData() {

    }


    @OnClick(R.id.choose_cinema_settable)
    public void onViewClicked() {
        chooseCinemaSettable.setScreenName(mScreeningHall + "荧幕");//设置屏幕名称
        chooseCinemaSettable.setMaxSelected(3);//设置最多选中

    }

    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.6f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }


    @OnClick({R.id.wechat_pay, R.id.wechat_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wechat_pay:
                xiaDan();
                break;
            case R.id.wechat_cancel:
                finish();
                break;
        }
    }

    private void xiaDan() {
        ChooseDialog chooseDialog = new ChooseDialog();
        chooseDialog.show(getSupportFragmentManager(), "");
    }

}
