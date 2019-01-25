package zmz.zhao.com.zmz.https;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import zmz.zhao.com.zmz.bean.Address;
import zmz.zhao.com.zmz.bean.Attention;
import zmz.zhao.com.zmz.bean.LoginBean;
import zmz.zhao.com.zmz.bean.MyMessage;
import zmz.zhao.com.zmz.bean.Result;

public interface IRequest {

    //登录
    @POST("user/v1/login")
    @FormUrlEncoded
    Observable<Result<LoginBean>> showLogin(@Field("phone") String phone, @Field("pwd") String pwd);

    //注册
    @POST("user/v1/registerUser")
    @FormUrlEncoded
    Observable<Result> showRegister(@Field("nickName") String nickName, @Field("phone") String phone, @Field("pwd") String pwd, @Field("pwd2") String pwd2, @Field("sex") int sex, @Field("birthday") String birthday, @Field("imei") String imei, @Field("ua") String ua, @Field("screenSize") String screenSize, @Field("os") String os, @Field("email") String email);

    //我的关注影片
    @GET("movie/v1/verify/findMoviePageList")
    Observable<Result<List<Attention>>> attention(@Header("userId") int userId,
                                                  @Header("sessionId") String sessionId,
                                                  @Query("page") int page,
                                                  @Query("count") int count);

    //我的关注影院
    @GET("cinema/v1/verify/findCinemaPageList")
    Observable<Result<List<Address>>> minecinema(@Header("userId") int userId,
                                                 @Header("sessionId") String sessionId,
                                                 @Query("page") int page,
                                                 @Query("count") int count);

    /**
     * @作者 啊哈
     * @date 2019/1/24
     * 我的信息
     */
    @GET("user/v1/verify/getUserInfoByUserId")
    Observable<Result<MyMessage>> mine(@Header("userId") int userId,
                                       @Header("sessionId") String sessionId);
}

