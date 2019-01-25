package zmz.zhao.com.zmz.presenter;

import io.reactivex.Observable;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.util.EncryptUtil;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/25 11:52
 * author:赵明珠(啊哈)
 * function:
 */
public class UpdatePwdPresenter extends BasePresenter{
    public UpdatePwdPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetworkManager.getInstance().create(IRequest.class);

        String oldpwd = EncryptUtil.encrypt((String) args[2]);
        String newpwd = EncryptUtil.encrypt((String) args[3]);
        String newpwd2 = EncryptUtil.encrypt((String) args[4]);

        return iRequest.Updatepwd((int)args[0],(String)args[1],oldpwd,newpwd,newpwd2);
    }
}
