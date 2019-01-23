package zmz.zhao.com.zmz.https;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import zmz.zhao.com.zmz.bean.LoginBean;
import zmz.zhao.com.zmz.bean.Result;

public interface IRequest {

    @POST("user/v1/login")
    @FormUrlEncoded
    Observable<Result<LoginBean>> showLogin(@Field("phone") String phone, @Field("pwd") String pwd);

    @POST("user/v1/registerUser")
    @FormUrlEncoded
    Observable<Result> showRegister(@Field("nickName") String nickName,@Field("phone") String phone,@Field("pwd") String pwd,@Field("pwd2") String pwd2,@Field("sex") int sex,@Field("birthday") String birthday,@Field("imei") String imei,@Field("ua") String ua,@Field("screenSize") String screenSize,@Field("os") String os,@Field("email") String email);


}

