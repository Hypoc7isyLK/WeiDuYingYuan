package zmz.zhao.com.zmz.presenter;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/25 19:40
 * author:赵明珠(啊哈)
 * function:购票记录
 */
public class RecordPresenter extends BasePresenter{

    private int page = 1;

    private int count = 5;

    private boolean isRefresh = true;

    public RecordPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        isRefresh = (boolean) args[2];

        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }

        IRequest iRequest = NetworkManager.getInstance().create(IRequest.class);

        return iRequest.record((int)args[0],(String)args[1],page,count,(int)args[3]);
    }
    public boolean isResresh() {

        return isRefresh;
    }
}
