package zmz.zhao.com.zmz.presenter;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/29 13:32
 * author:赵明珠(啊哈)
 * function:
 */
public class CommentPresenter extends BasePresenter{

    private int page = 1;
    private int count = 10;
    private boolean isRefresh = true;

    public CommentPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        isRefresh = (boolean) args[3];

        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }
        IRequest iRequest = NetworkManager.getInstance().create(IRequest.class);

        return iRequest.comment((int)args[0],(String)args[1],(int)args[2],page,count);
    }
    public boolean isResresh() {

        return isRefresh;
    }
}
