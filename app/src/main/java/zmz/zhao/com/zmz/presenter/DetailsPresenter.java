package zmz.zhao.com.zmz.presenter;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/27
 * author:李阔(淡意衬优柔)
 * function:
 */
public class DetailsPresenter extends BasePresenter {

    private IRequest mIRequest;

    public DetailsPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        mIRequest = NetworkManager.getInstance().create(IRequest.class);
        return mIRequest.showDetails((int) args[0],(String)args[1],(String)args[2]);
    }
}
