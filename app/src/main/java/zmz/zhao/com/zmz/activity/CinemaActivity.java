package zmz.zhao.com.zmz.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;
import zmz.zhao.com.zmz.adapter.ScheduleAdapter;
import zmz.zhao.com.zmz.adapter.ScheduleListAdapter;
import zmz.zhao.com.zmz.bean.PayBean;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.bean.ScheduleCinemaBean;
import zmz.zhao.com.zmz.bean.ScheduleListBean;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.presenter.PlaceanOrderPresenter;
import zmz.zhao.com.zmz.presenter.ScheduleCinemaPresenter;
import zmz.zhao.com.zmz.presenter.ScheduleListPresenter;
import zmz.zhao.com.zmz.util.MD5Utils;
import zmz.zhao.com.zmz.view.DataCall;

public class CinemaActivity extends BaseActivity {

    @BindView(R.id.yingyuan)
    RelativeLayout yingyuan;
    @BindView(R.id.details_simple)
    SimpleDraweeView detailsSimple;
    @BindView(R.id.details_title)
    TextView detailsTitle;
    @BindView(R.id.details_message)
    TextView detailsMessage;
    @BindView(R.id.list)
    RecyclerCoverFlow list;
    @BindView(R.id.movie_text_xian)
    TextView movieTextXian;
    @BindView(R.id.movie_text_dong)
    TextView movieTextDong;
    @BindView(R.id.paiqi)
    RecyclerView paiqi;
    private Intent mIntent;
    private String mId;
    private PlaceanOrderPresenter mPlaceanOrderPresenter;
    private String mSessionId;
    private int mUserId;
    private String mString;
    private IWXAPI api;
    private String mAddress;
    private String mLogo;
    private String mName;
    private ScheduleCinemaPresenter mScheduleCinemaPresenter;
    private ScheduleAdapter mScheduleAdapter;
    private int mWidth;
    private int mItemCount;
    private int mCoun;
    private ScheduleListPresenter mScheduleListPresenter;
    private ScheduleListAdapter mScheduleListAdapter;
    private String cinemaname;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cinema;
    }

    @Override
    protected void initView() {
        /**
         * 微信支付
         */
        api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516");//第二个参数为APPID
        api.registerApp("wxb3852e6a6b7d9516");

        mScheduleAdapter = new ScheduleAdapter(this);
        mScheduleListAdapter = new ScheduleListAdapter(this);

        mIntent = getIntent();
        mId = mIntent.getStringExtra("id");
        mAddress = mIntent.getStringExtra("address");
        mLogo = mIntent.getStringExtra("logo");
        mName = mIntent.getStringExtra("name");

        detailsSimple.setImageURI(mLogo);
        detailsTitle.setText(mName);
        detailsMessage.setText(mAddress);
        list.setAdapter(mScheduleAdapter);

        list.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                int selectedPos = list.getSelectedPos();
                ObjectAnimator animator = ObjectAnimator.ofFloat(movieTextDong, "translationX", mCoun * (selectedPos));
                animator.setDuration(500);
                animator.start();
            }
        });



        mScheduleCinemaPresenter = new ScheduleCinemaPresenter(new ScheduleCall());
        mScheduleCinemaPresenter.reqeust(mId);


        paiqi.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));

        mScheduleAdapter.setOnClickListener(new ScheduleAdapter.OnClickListener() {
            @Override
            public void scuccess(int id,String name) {
                String iid= String.valueOf(id);
                cinemaname = name;
                paiqi.setAdapter(mScheduleListAdapter);
                mScheduleListPresenter = new ScheduleListPresenter(new ScheduleListCall());
                mScheduleListPresenter.reqeust(mId,iid);
            }
        });

        mScheduleListAdapter.setOnClickListener(new ScheduleListAdapter.OnClickListener() {
            @Override
            public void scuccess(int id,String price,String screeningHall) {
                Intent intent = new Intent(CinemaActivity.this,ChooseActivity.class);
                intent.putExtra("name",mName);
                intent.putExtra("address",mAddress);
                intent.putExtra("id",id);
                intent.putExtra("price",price);
                intent.putExtra("screeningHall",screeningHall);
                intent.putExtra("cinemaname",cinemaname);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void destoryData() {

    }


    @OnClick(R.id.yingyuan)
    public void onViewClicked() {

        /**
         * 微信支付
         */
        mSessionId = USER_INFO.getSessionId();
        mUserId = USER_INFO.getUserId();

        mString = MD5Utils.md5Password(mUserId + "590" + "1" + "movie");
        mPlaceanOrderPresenter = new PlaceanOrderPresenter(new PlaceOrderCall());
        mPlaceanOrderPresenter.reqeust(mUserId, mSessionId, 590, 1, mString);


    }

    /**
     * 微信支付
     */
    private class PlaceOrderCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {

                Toast.makeText(CinemaActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(CinemaActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class ScheduleCall implements DataCall<Result<List<ScheduleCinemaBean>>> {

        private List<ScheduleCinemaBean> mResult;

        @Override
        public void success(Result<List<ScheduleCinemaBean>> result) {
            mResult = result.getResult();
            mScheduleAdapter.reset(mResult);

            mWidth = movieTextXian.getWidth();
            mItemCount = mScheduleAdapter.getItemCount();
            mCoun = mWidth / mItemCount;

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class ScheduleListCall implements DataCall<Result<List<ScheduleListBean>>> {
        @Override
        public void success(Result<List<ScheduleListBean>> result) {
            List<ScheduleListBean> result1 = result.getResult();
            mScheduleListAdapter.reset(result1);
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
