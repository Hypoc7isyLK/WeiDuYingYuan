package zmz.zhao.com.zmz.presenter;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/2/13 20:02
 * author:赵明珠(啊哈)
 * function:
 */
public class FocusMovieOffPresenter extends BasePresenter{
    public FocusMovieOffPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.getInstance().create(IRequest.class);


        return iRequest.focusoff((int)args[0],(String)args[1],(int)args[2]);
    }
}
