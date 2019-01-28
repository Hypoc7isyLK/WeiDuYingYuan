package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bw.movie.R;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zmz.zhao.com.zmz.bean.PayBean;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.presenter.PlaceanOrderPresenter;
import zmz.zhao.com.zmz.util.MD5Utils;
import zmz.zhao.com.zmz.view.DataCall;

public class CinemaActivity extends BaseActivity {

    @BindView(R.id.yingyuan)
    RelativeLayout yingyuan;
    private Intent mIntent;
    private String mId;
    private PlaceanOrderPresenter mPlaceanOrderPresenter;
    private String mSessionId;
    private int mUserId;
    private String mString;
    private IWXAPI api;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cinema;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mIntent = getIntent();
        mId = mIntent.getStringExtra("id");
        api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516");//第二个参数为APPID
        api.registerApp("wxb3852e6a6b7d9516");


    }


    @Override
    protected void destoryData() {

    }



    @OnClick(R.id.yingyuan)
    public void onViewClicked() {
        mSessionId = USER_INFO.getSessionId();
        mUserId = USER_INFO.getUserId();

        mString = MD5Utils.md5Password(mUserId + "590" + "1" + "movie");
        mPlaceanOrderPresenter = new PlaceanOrderPresenter(new PlaceOrderCall());
        mPlaceanOrderPresenter.reqeust(mUserId,mSessionId,590,1,mString);


    }

    private class PlaceOrderCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){

                Toast.makeText(CinemaActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("lk","订单号"+result.getOrderId());
                String orderId = result.getOrderId();
                IRequest interfacea = NetworkManager.getInstance().create(IRequest.class);
                interfacea.pay(USER_INFO.getUserId(),USER_INFO.getSessionId()+"","1",orderId).subscribeOn(Schedulers.newThread())
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



            }else {
                Toast.makeText(CinemaActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
