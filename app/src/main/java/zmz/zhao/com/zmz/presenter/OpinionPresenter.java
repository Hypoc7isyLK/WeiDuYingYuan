package zmz.zhao.com.zmz.presenter;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/24 20:49
 * author:赵明珠(啊哈)
 * function:
 */
public class OpinionPresenter extends BasePresenter{

    public OpinionPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.getInstance().create(IRequest.class);

        return iRequest.opinion((int)args[0],(String)args[1],(String)args[2]);
    }
}
