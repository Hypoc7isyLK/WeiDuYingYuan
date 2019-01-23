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
public class RegisterPresenter extends BasePresenter{

    private IRequest mIRequest;

    public RegisterPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        mIRequest = NetworkManager.getInstance().create(IRequest.class);
        return mIRequest.showRegister((String)args[0],(String)args[1],(String)args[2],(String)args[3],(int)args[4],(String)args[5],(String)args[6],(String)args[7],(String)args[8],(String)args[9],(String)args[10]);
    }
}
