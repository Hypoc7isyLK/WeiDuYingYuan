package zmz.zhao.com.zmz.presenter;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/30 9:57
 * author:赵明珠(啊哈)
 * function:
 */
public class TheatrePresnter extends BasePresenter{

    public TheatrePresnter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetworkManager.getInstance().create(IRequest.class);

        int id = Integer.parseInt((String) args[0]);

        return iRequest.theart(id);
    }
}
