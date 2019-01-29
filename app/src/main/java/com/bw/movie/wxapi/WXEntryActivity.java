package com.bw.movie.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.widget.Toast;

import com.bw.movie.R;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.UserInfoDao;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.UserInfoDao;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import zmz.zhao.com.zmz.bean.LoginBean;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.WeChatLoginPresenter;
import zmz.zhao.com.zmz.view.DataCall;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

	public static final String WEIXIN_APP_ID = "wxb3852e6a6b7d9516";
    private IWXAPI api;
	private WeChatLoginPresenter mWeChatLoginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);


        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);



        api.handleIntent(getIntent(), this);
    }



    @Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {
		//Toast.makeText(this, "errorcode = " + resp.errCode, Toast.LENGTH_SHORT).show();
		String result = "";
		if (resp.errCode==0){
			SendAuth.Resp req = (SendAuth.Resp)resp;
			Toast.makeText(this, req.code, Toast.LENGTH_SHORT).show();
			mWeChatLoginPresenter = new WeChatLoginPresenter(new WechatCall());
			mWeChatLoginPresenter.reqeust(req.code);
			finish();
		}

	}

	private class WechatCall implements DataCall<Result<LoginBean>> {
		@Override
		public void success(Result<LoginBean> result) {

                if (result.getStatus().equals("0000")) {

					result.getResult().getUserInfo().setUserId(result.getResult().getUserId());
					result.getResult().getUserInfo().setSessionId(result.getResult().getSessionId());

					result.getResult().getUserInfo().setStatus(1);

					UserInfoDao userInfoDao = DaoMaster.newDevSession(getBaseContext(), UserInfoDao.TABLENAME).getUserInfoDao();

					userInfoDao.insertOrReplace(result.getResult().getUserInfo());

                    Toast.makeText(WXEntryActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(WXEntryActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }

		}

		@Override
		public void fail(ApiException e) {

		}
	}




}