package zmz.zhao.com.zmz.https;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import zmz.zhao.com.zmz.bean.LoginBean;
import zmz.zhao.com.zmz.bean.Result;

public interface IRequest {

    @POST
    @FormUrlEncoded
    Observable<Result<LoginBean>> showLogin(@Field("phone") String phone, @Field("pwd") String pwd);



}

