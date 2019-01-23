package zmz.zhao.com.zmz.presenter;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/23
 * author:李阔(淡意衬优柔)
 * function:
 */
public class LoginPresenter extends BasePresenter{

    private IRequest mIRequest;

    public LoginPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        mIRequest = NetworkManager.getInstance().create(IRequest.class);
        return mIRequest.showLogin((String)args[0],(String)args[1]);
    }
}
