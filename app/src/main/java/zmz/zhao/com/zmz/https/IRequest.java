package zmz.zhao.com.zmz.https;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import zmz.zhao.com.zmz.bean.Address;
import zmz.zhao.com.zmz.bean.Attention;
import zmz.zhao.com.zmz.bean.CinemaListBean;
import zmz.zhao.com.zmz.bean.Comment;
import zmz.zhao.com.zmz.bean.CommentReply;
import zmz.zhao.com.zmz.bean.DetailsBean;
import zmz.zhao.com.zmz.bean.LoginBean;
import zmz.zhao.com.zmz.bean.MineMassage;
import zmz.zhao.com.zmz.bean.MyMessage;
import zmz.zhao.com.zmz.bean.PayBean;
import zmz.zhao.com.zmz.bean.Record;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.bean.ScheduleCinemaBean;
import zmz.zhao.com.zmz.bean.ScheduleListBean;
import zmz.zhao.com.zmz.bean.ShowLunBoBean;
import zmz.zhao.com.zmz.bean.SystemMassage;

public interface IRequest {

    /**
     * 登陆
     */
    @POST("user/v1/login")
    @FormUrlEncoded
    Observable<Result<LoginBean>> showLogin(@Field("phone") String phone,
                                            @Field("pwd") String pwd);

    /**
     * 注册
     */
    @POST("user/v1/registerUser")
    @FormUrlEncoded
    Observable<Result> showRegister(@Field("nickName") String nickName,
                                    @Field("phone") String phone,
                                    @Field("pwd") String pwd,
                                    @Field("pwd2") String pwd2,
                                    @Field("sex") int sex,
                                    @Field("birthday") String birthday,
                                    @Field("imei") String imei,
                                    @Field("ua") String ua,
                                    @Field("screenSize") String screenSize,
                                    @Field("os") String os,
                                    @Field("email") String email);

    /**
     * @作者 啊哈
     * @date 2019/1/24
     * 关注影片
     */
    @GET("movie/v1/verify/findMoviePageList")
    Observable<Result<List<Attention>>> attention(@Header("userId") int userId,
                                                  @Header("sessionId") String sessionId,
                                                  @Query("page") int page,
                                                  @Query("count") int count);

    /**
     * @作者 啊哈
     * @date 2019/1/25
     * @method：签到
     */
    @GET("user/v1/verify/userSignIn")
    Observable<Result> sign(@Header("userId") int userId,
                            @Header("sessionId") String sessionId);

    /**
     * @作者 啊哈
     * @date 2019/1/24
     * 关注影院
     */
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

    /**
     * @作者 啊哈
     * @date 2019/1/24
     * 修改我的信息
     */
    @FormUrlEncoded
    @POST("user/v1/verify/modifyUserInfo")
    Observable<Result<MyMessage>> update(@Header("userId") int userId,
                                         @Header("sessionId") String sessionId,
                                         @Field("nickName") String nickName,
                                         @Field("sex") int sex,
                                         @Field("email") String email);

    /**
     * @作者 啊哈
     * @date 2019/1/24
     * 意见反馈
     */
    @FormUrlEncoded
    @POST("tool/v1/verify/recordFeedBack")
    Observable<Result> opinion(@Header("userId") int userId,
                               @Header("sessionId") String sessionId,
                               @Field("content") String content);


    /**
     * @作者 啊哈
     * @date 2019/1/25
     * 购票记录
     */
    @GET("user/v1/verify/findUserBuyTicketRecordList")
    Observable<Result<List<Record>>> record(@Header("userId") int userId,
                                            @Header("sessionId") String sessionId,
                                            @Query("page") int page,
                                            @Query("count") int count,
                                            @Query("status") int status);

    /**
     * @作者 啊哈
     * @date 2019/1/25
     * @method:修改密码
     */
    @FormUrlEncoded
    @POST("user/v1/verify/modifyUserPwd")
    Observable<Result> Updatepwd(@Header("userId") int userId,
                                 @Header("sessionId") String sessionId,
                                 @Field("oldPwd") String oldPwd,
                                 @Field("newPwd") String newPwd,
                                 @Field("newPwd2") String newPwd2);


    /**
     * 首页轮播+热门电影
     */
    @GET("movie/v1/findHotMovieList")
    Observable<Result<List<ShowLunBoBean>>> showLunbo(@Header("userId") int userId,
                                                      @Header("sessionId") String sessionId,
                                                      @Query("page") String page,
                                                      @Query("count") String count);

    /**
     * @作者 啊哈
     * @date 2019/1/26
     * @method：上传头像
     */
    @POST("user/v1/verify/uploadHeadPic")
    Observable<Result> heard(@Header("userId") int userid,
                             @Header("sessionId") String session,
                             @Body MultipartBody image);

    /**
     * 正在热映
     */
    @GET("movie/v1/findReleaseMovieList")
    Observable<Result<List<ShowLunBoBean>>> hotShowing(@Header("userId") int userId,
                                                       @Header("sessionId") String sessionId,
                                                       @Query("page") String page,
                                                       @Query("count") String count);

    /**
     * 即将上映
     */
    @GET("movie/v1/findComingSoonMovieList")
    Observable<Result<List<ShowLunBoBean>>> commingSun(@Header("userId") int userId,
                                                       @Header("sessionId") String sessionId,
                                                       @Query("page") String page,
                                                       @Query("count") String count);

    /**
     * @作者 啊哈
     * @date 2019/1/26
     * @method：个人状态信息
     */
    @GET("user/v1/verify/findUserHomeInfo")
    Observable<Result<MineMassage>> minemassage(@Header("userId") int userId,
                                                @Header("sessionId") String sessionId);

    /**
     * @作者 啊哈
     * @date 2019/1/26
     * @method：系统消息
     */
    @GET("tool/v1/verify/findAllSysMsgList")
    Observable<Result<List<SystemMassage>>> systemmassage(@Header("userId") int userId,
                                                          @Header("sessionId") String sessionId,
                                                          @Query("page") int page,
                                                          @Query("count") int count);

    /**
     * 电影详情
     */
    @GET("movie/v1/findMoviesDetail")
    Observable<Result<DetailsBean>> showDetails(@Header("userId") int userId,
                                                @Header("sessionId") String sessionId,
                                                @Query("movieId") String movieId);

    /**
     * @作者 啊哈
     * @date 2019/1/27
     * @method：更改消息状态
     */
    @GET("tool/v1/verify/changeSysMsgStatus")
    Observable<Result> change(@Header("userId") int userId,
                              @Header("sessionId") String sessionId,
                              @Query("id") int page);

    @GET("cinema/v1/findRecommendCinemas")
    Observable<Result<List<CinemaListBean>>> showCinema(@Header("userId") int userId,
                                                        @Header("sessionId") String sessionId,
                                                        @Query("page") String page,
                                                        @Query("count") String count);

    @GET("cinema/v1/findNearbyCinemas")
    Observable<Result<List<CinemaListBean>>> shownearbyCinema(@Header("userId") int userId,
                                                              @Header("sessionId") String sessionId,
                                                              @Query("longitude") String longitude,
                                                              @Query("latitude") String latitude,
                                                              @Query("page") String page,
                                                              @Query("count") String count);

    @GET("movie/v1/findMovieListByCinemaId")
    Observable<Result<List<ScheduleCinemaBean>>> showScheduleCinema(@Query("cinemaId") String cinemaId);


    @POST("movie/v1/verify/buyMovieTicket")
    @FormUrlEncoded
    Observable<Result> showXiaDanCinema(@Header("userId") int userId,
                                        @Header("sessionId") String sessionId,
                                        @Field("scheduleId") int scheduleId,
                                        @Field("amount") int amount,
                                        @Field("sign") String sign);


    @POST("movie/v1/verify/pay")
    @FormUrlEncoded
    Observable<PayBean> pay(@Header("userId") int userId,
                            @Header("sessionId") String sessionId,
                            @Field("payType") String payType,
                            @Field("orderId") String orderId);

    @POST("user/v1/weChatBindingLogin")
    @FormUrlEncoded
    Observable<Result<LoginBean>> wechatlogin(@Field("code") String code);


    @GET("movie/v1/findMovieScheduleList")
    Observable<Result<List<ScheduleListBean>>> showScheduleList(@Query("cinemasId") String cinemaId,
                                                                @Query("movieId") String movieId);


    /**
     * @作者 啊哈
     * @date 2019/1/29
     * @method：关注电影
     */
    @GET("movie/v1/verify/followMovie")
    Observable<Result> focus(@Header("userId") int userId,
                             @Header("sessionId") String sessionId,
                             @Query("movieId") int id);

    /**
     * @作者 啊哈
     * @date 2019/2/13
     * @method：取消关注
     */
    @GET("movie/v1/verify/cancelFollowMovie")
    Observable<Result> focusoff(@Header("userId") int userId,
                                @Header("sessionId") String sessionId,
                                @Query("movieId") int id);

    /**
     * @作者 啊哈
     * @date 2019/2/14
     * @method：关注影院
     */
    @GET("cinema/v1/verify/followCinema")
    Observable<Result> focusCinema(@Header("userId") int userId,
                             @Header("sessionId") String sessionId,
                             @Query("cinemaId") int id);
    /**
     * @作者 啊哈
     * @date 2019/2/13
     * @method：取消关注
     */
    @GET("cinema/v1/verify/cancelFollowCinema")
    Observable<Result> focusoffCinema(@Header("userId") int userId,
                                @Header("sessionId") String sessionId,
                                @Query("cinemaId") int id);

    /**
     * @作者 啊哈
     * @date 2019/1/29
     * @method：影评
     */
    @GET("movie/v1/findAllMovieComment")
    Observable<Result<List<Comment>>> comment(@Header("userId") int userId,
                                              @Header("sessionId") String sessionId,
                                              @Query("movieId") int id,
                                              @Query("page") int page,
                                              @Query("count") int count);

    /**
     * @作者 啊哈
     * @date 2019/1/30
     * @method：根据影片选择影院
     */
    @GET("movie/v1/findCinemasListByMovieId")
    Observable<Result<List<CinemaListBean>>> theart(@Query("movieId") int movieId);

    /**
     * @作者 啊哈
     * @date 2019/2/12
     * @method：影片排期
     */
    @GET("movie/v1/findMovieScheduleList")
    Observable<Result<List<ScheduleListBean>>> filmSche(@Query("cinemasId") int cinemasId, @Query("movieId") int movieId);

    /**
     * @作者 啊哈
     * @date 2019/2/13
     * @method：影评点赞
     */
    @POST("movie/v1/verify/movieCommentGreat")
    @FormUrlEncoded
    Observable<Result> state(@Header("userId") int userId,
                             @Header("sessionId") String sessionId,
                             @Field("commentId") int commentId);
    /**
     * @作者 啊哈
     * @date 2019/2/14
     * @method：评论回复
     */
    @GET("movie/v1/findCommentReply")
    Observable<Result<List<CommentReply>>> commentReply(@Header("userId") int userId,
                                                        @Header("sessionId") String sessionId,
                                                        @Query("commentId") int id,
                                                        @Query("page") int page,
                                                        @Query("count") int count);

}

