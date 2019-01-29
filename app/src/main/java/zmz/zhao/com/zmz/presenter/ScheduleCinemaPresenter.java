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
public class ScheduleCinemaPresenter extends BasePresenter {

    private IRequest mIRequest;

    public ScheduleCinemaPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        mIRequest = NetworkManager.getInstance().create(IRequest.class);
        return mIRequest.showScheduleCinema((String) args[0]);
    }
}
