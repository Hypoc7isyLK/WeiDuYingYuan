package zmz.zhao.com.zmz.view;

import zmz.zhao.com.zmz.exception.ApiException;

public interface DataCall<T> {
    void success(T result);
    void fail(ApiException e);
}
