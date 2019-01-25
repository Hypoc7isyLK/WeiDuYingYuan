package zmz.zhao.com.zmz.presenter;

import android.util.Log;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/24 19:29
 * author:赵明珠(啊哈)
 * function:
 */
public class UpdatePresenter extends BasePresenter{

    public UpdatePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.getInstance().create(IRequest.class);
        Log.e("zmz","====="+(int)args[3]);
        return iRequest.update((int)args[0],(String)args[1],(String)args[2],(int)args[3],(String)args[4]);
    }
}
