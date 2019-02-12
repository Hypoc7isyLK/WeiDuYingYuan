package zmz.zhao.com.zmz.presenter;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/2/12 16:47
 * author:赵明珠(啊哈)
 * function:
 */
public class FilmSchePresenter extends BasePresenter{

    public FilmSchePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetworkManager.getInstance().create(IRequest.class);

        return iRequest.filmSche((int)args[0],(int)args[0]);
    }
}
