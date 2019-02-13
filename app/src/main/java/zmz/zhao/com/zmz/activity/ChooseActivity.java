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
import android.widget.Toast;

import com.bw.movie.R;
import com.qfdqc.views.seattable.SeatTable;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zmz.zhao.com.zmz.bean.PayBean;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.dialog.ChooseDialog;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.presenter.PlaceanOrderPresenter;
import zmz.zhao.com.zmz.util.MD5Utils;
import zmz.zhao.com.zmz.view.DataCall;
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
    TextView chooseCinemaMarquee;
    private Intent mIntent;
    private String mId;
    private String mmPrice;
    private String mScreeningHall;
    private String mAddress;
    private String mName;
    private String mCinemaname;
    private int count;
    private IWXAPI api;
    private String mSessionId;
    private int mUserId;
    private String mString;
    private PlaceanOrderPresenter mPlaceanOrderPresenter;
    private String mEndtime;
    private String mBegintime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose;
    }

    @Override
    protected void initView() {
        /**
         * 微信支付
         */
        api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516");//第二个参数为APPID
        api.registerApp("wxb3852e6a6b7d9516");
        ButterKnife.bind(this);
        mIntent = getIntent();
        mId = mIntent.getStringExtra("id");

        Log.e("lk","mcid"+mId);
        mmPrice = mIntent.getStringExtra("price");
        mScreeningHall = mIntent.getStringExtra("screeningHall");
        mAddress = mIntent.getStringExtra("address");
        mName = mIntent.getStringExtra("name");
        mCinemaname = mIntent.getStringExtra("cinemaname");
        mEndtime = mIntent.getStringExtra("endtime");
        mBegintime = mIntent.getStringExtra("begintime");

        chooseCinemaName.setText(mCinemaname);
        chooseCinemaAddress.setText(mAddress);
        chooseMovieName.setText(mName);
        chooseCinemaMarquee.setText(mBegintime+"——"+mEndtime+"  "+"中国I Do巨幕全景声听");


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
        count = selectedTableCount;
        Log.e("lk","选中的个数+"+count);

    }

    //取消选座时价格联动
    private void changePriceWithUnSelected() {
        selectedTableCount--;
        String currentPrice = mPriceWithCalculate.multiply(new BigDecimal(String.valueOf(selectedTableCount))).toString();
        SpannableString spannableString = changTVsize(currentPrice);
        chooseMoviePrice.setText(spannableString);
        count = selectedTableCount;
        Log.e("lk","选中的个数-"+count);
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
        if (USER_INFO==null){
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        }else {
            mSessionId = USER_INFO.getSessionId();
            mUserId = USER_INFO.getUserId();
            if (count == 0){
                Toast.makeText(this, "至少选择一个座位", Toast.LENGTH_SHORT).show();
            }else {
                String mCOUNT = String.valueOf(count);
                Log.e("lk","mmmmmmcount"+mCOUNT);

                Log.e("lk","mmmid"+mId);


                mString = MD5Utils.md5Password(mUserId +mId + mCOUNT + "movie");
                int id = Integer.parseInt(mId);
                mPlaceanOrderPresenter = new PlaceanOrderPresenter(new PlaceOrderCall());

                mPlaceanOrderPresenter.reqeust(mUserId, mSessionId, id, count, mString);
            }

        }
    }


    /**
     * 微信支付
     */
    private class PlaceOrderCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {

                Toast.makeText(ChooseActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("lk", "订单号" + result.getOrderId());
                String orderId = result.getOrderId();
                IRequest interfacea = NetworkManager.getInstance().create(IRequest.class);
                interfacea.pay(USER_INFO.getUserId(), USER_INFO.getSessionId() + "", "1", orderId).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<PayBean>() {
                            @Override
                            public void accept(PayBean payBean) throws Exception {
                                PayReq req = new PayReq();
                                req.appId = payBean.getAppId();
                                req.partnerId = payBean.getPartnerId();
                                req.prepayId = payBean.getPrepayId();
                                req.nonceStr = payBean.getNonceStr();
                                req.timeStamp = payBean.getTimeStamp();
                                req.packageValue = payBean.getPackageValue();
                                req.sign = payBean.getSign();
                                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                //3.调用微信支付sdk支付方法
                                api.sendReq(req);
                            }
                        });


            } else {
                Toast.makeText(ChooseActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("选座页面");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("选座页面");
        MobclickAgent.onPause(this);
    }

}
