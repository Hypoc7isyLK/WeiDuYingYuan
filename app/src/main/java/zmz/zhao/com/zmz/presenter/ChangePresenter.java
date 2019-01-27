package zmz.zhao.com.zmz.presenter;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/27 11:02
 * author:赵明珠(啊哈)
 * function:
 */
public class ChangePresenter extends BasePresenter{

    public ChangePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetworkManager.getInstance().create(IRequest.class);

        return iRequest.change((int)args[0],(String)args[1],(int)args[2]);
    }
}
