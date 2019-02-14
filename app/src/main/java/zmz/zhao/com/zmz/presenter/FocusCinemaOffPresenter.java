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
public class FocusCinemaOffPresenter extends BasePresenter{
    public FocusCinemaOffPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.getInstance().create(IRequest.class);
        return iRequest.focusoffCinema((int)args[0],(String)args[1],(int)args[2]);
    }
}
