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
public class PlaceanOrderPresenter extends BasePresenter {

    private IRequest mIRequest;

    public PlaceanOrderPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        mIRequest = NetworkManager.getInstance().create(IRequest.class);
        return mIRequest.showXiaDanCinema((int) args[0],(String)args[1],(int)args[2],(int)args[3],(String)args[4]);
    }
}
