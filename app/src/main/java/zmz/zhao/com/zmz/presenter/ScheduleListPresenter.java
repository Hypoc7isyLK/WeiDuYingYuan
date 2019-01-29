package zmz.zhao.com.zmz.presenter;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/29
 * author:李阔(淡意衬优柔)
 * function:
 */
public class ScheduleListPresenter extends BasePresenter {
    public ScheduleListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.getInstance().create(IRequest.class);
        return iRequest.showScheduleList((String)args[0],(String)args[1]);
    }
}
