package zmz.zhao.com.zmz.presenter;

import android.annotation.SuppressLint;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.CustomException;
import zmz.zhao.com.zmz.exception.ResponseTransformer;
import zmz.zhao.com.zmz.view.DataCall;

public abstract class BasePresenter {
    private DataCall dataCall;

    boolean running;

    public BasePresenter(DataCall dataCall) {
        this.dataCall = dataCall;
    }

    protected abstract Observable observable(Object... args);

    @SuppressLint("CheckResult")
    public void reqeust(Object... args) {

        if (running ) {
            return;
        }

        running = true;

        observable(args)
                .compose(ResponseTransformer.handleResult())
                .compose(new ObservableTransformer() {
                    @Override
                    public ObservableSource apply(Observable upstream) {
                        return upstream.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        running = false;
                        dataCall.success(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        running = false;
                        dataCall.fail(CustomException.handleException(throwable));
                    }
                });

    }
    public void unBind(){

        dataCall = null;
    }
    public boolean Running(){
        return running;
    }
}
