package zmz.zhao.com.zmz.presenter;

import android.util.Log;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/23 20:35
 * author:赵明珠(啊哈)
 * function:
 */
public class MineMoviePresenter extends BasePresenter{

    private int page = 1;
    private int count = 10;
    private boolean isRefresh = true;

    public MineMoviePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object...args) {

        isRefresh = (boolean) args[2];

        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }
        IRequest iRequest = NetworkManager.getInstance().create(IRequest.class);

        return iRequest.attention((int)args[0],(String)args[1],page,count);
    }
    public boolean isResresh() {

        return isRefresh;
    }
}
