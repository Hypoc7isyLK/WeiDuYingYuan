package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zmz.zhao.com.zmz.bean.DetailsBean;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.DetailsPresenter;
import zmz.zhao.com.zmz.view.DataCall;

public class InsideDetailsActivity extends BaseActivity {


    @BindView(R.id.xiaoxinxin)
    ImageView xiaoxinxin;
    @BindView(R.id.insidetails_title)
    TextView insidetailsTitle;
    @BindView(R.id.insidetails_simple)
    SimpleDraweeView insidetailsSimple;
    @BindView(R.id.insidetails_details)
    Button insidetailsDetails;
    @BindView(R.id.insidetails_foreshow)
    Button insidetailsForeshow;
    @BindView(R.id.insidetails_photo)
    Button insidetailsPhoto;
    @BindView(R.id.insidetails_discuss)
    Button insidetailsDiscuss;
    @BindView(R.id.Focus_back)
    ImageView FocusBack;
    @BindView(R.id.insidetails_buy)
    ImageView insidetailsBuy;
    private DetailsPresenter mDetailsPresenter;
    private String mSessionId;
    private int mUserId;
    private String mId1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_inside_details;
    }

    @Override
    protected void initView() {
        mSessionId = USER_INFO.getSessionId();
        mUserId = USER_INFO.getUserId();
        ButterKnife.bind(this);
        Intent mIntent = getIntent();
        mId1 = mIntent.getStringExtra("id");
        Log.e("lk","inside"+mId1);

        /*if (mFollow == 1){
            xiaoxinxin.setImageResource(R.mipmap.com_icon_collection_selected);
        }else {
            xiaoxinxin.setImageResource(R.mipmap.com_icon_collection_default);
        }*/

        mDetailsPresenter = new DetailsPresenter(new DetailsCall());
        mDetailsPresenter.reqeust(mUserId,mSessionId,mId1);
    }

    @Override
    protected void destoryData() {
        mDetailsPresenter=null;
    }

    @OnClick({R.id.xiaoxinxin, R.id.insidetails_details, R.id.insidetails_foreshow, R.id.insidetails_photo, R.id.insidetails_discuss, R.id.Focus_back, R.id.insidetails_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xiaoxinxin:
                break;
            case R.id.insidetails_details:
                break;
            case R.id.insidetails_foreshow:
                break;
            case R.id.insidetails_photo:
                break;
            case R.id.insidetails_discuss:
                break;
            case R.id.Focus_back:
                finish();
                break;
            case R.id.insidetails_buy:
                break;
        }
    }

    private class DetailsCall implements DataCall<Result<DetailsBean>> {

        private DetailsBean mResult;

        @Override
        public void success(Result<DetailsBean> result) {
            mResult = result.getResult();
            insidetailsSimple.setImageURI(mResult.getImageUrl());
            insidetailsTitle.setText(mResult.getName());
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
