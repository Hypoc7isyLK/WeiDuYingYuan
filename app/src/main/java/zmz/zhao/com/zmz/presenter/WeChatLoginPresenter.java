package zmz.zhao.com.zmz.presenter;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/28
 * author:李阔(淡意衬优柔)
 * function:
 */
public class WeChatLoginPresenter extends BasePresenter {

    private IRequest mIRequest;

    public WeChatLoginPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        mIRequest = NetworkManager.getInstance().create(IRequest.class);
        return mIRequest.wechatlogin((String) args[0]);
    }
}
