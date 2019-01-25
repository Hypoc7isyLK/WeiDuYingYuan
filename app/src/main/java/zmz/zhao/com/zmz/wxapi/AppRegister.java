package zmz.zhao.com.zmz.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import zmz.zhao.com.zmz.app.MyApplication;

public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
		msgApi.registerApp(MyApplication.APP_ID);
	}
}
