package zmz.zhao.com.zmz.presenter;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/2/14 19:45
 * author:赵明珠(啊哈)
 * function:
 */
public class VersionsPresenter extends BasePresenter{
    public VersionsPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.getInstance().create(IRequest.class);
        return iRequest.versions((int)args[0],(String)args[1],(String)args[2]);
    }
}
