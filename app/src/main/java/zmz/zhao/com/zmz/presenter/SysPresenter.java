package zmz.zhao.com.zmz.presenter;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/26 20:47
 * author:赵明珠(啊哈)
 * function:系统消息
 */
public class SysPresenter extends BasePresenter{

    private int page = 1;
    private int count = 5;
    private boolean isRefresh = true;

    public SysPresenter(DataCall dataCall) {
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

        return iRequest.systemmassage((int)args[0],(String)args[0],page,count);
    }

    public boolean isResresh() {

        return isRefresh;
    }
}
