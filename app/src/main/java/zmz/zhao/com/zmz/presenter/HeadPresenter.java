package zmz.zhao.com.zmz.presenter;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/26 13:46
 * author:赵明珠(啊哈)
 * function:
 */
public class HeadPresenter extends BasePresenter{

    public HeadPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.getInstance().create(IRequest.class);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("headPath", (String)args[2]);
        File file = new File((String) args[2]);
        builder.addFormDataPart("image", file.getName(),
                RequestBody.create(MediaType.parse("multipart/octet-stream"),
                        file));

        //Log.e("ZMZ","========="+builder. build().toString());


        return iRequest.heard((int)args[0],(String)args[1],builder.build());
    }
}
