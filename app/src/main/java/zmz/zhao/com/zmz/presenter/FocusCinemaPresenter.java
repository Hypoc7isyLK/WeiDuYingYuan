package zmz.zhao.com.zmz.presenter;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/2/14 14:16
 * author:赵明珠(啊哈)
 * function:
 */
public class FocusCinemaPresenter extends BasePresenter{
    public FocusCinemaPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.getInstance().create(IRequest.class);
        return iRequest.focusCinema((int)args[0],(String)args[1],(int)args[2]);
    }
}
