package zmz.zhao.com.zmz.bean;

/**
 * date:2019/1/25 10:25
 * author:赵明珠(啊哈)
 * function:购票记录
 */
public class Record {

    private int amount;
    private String beginTime;
    private String cinemaName;
    private long createTime;
    private String endTime;
    private int id;
    private String movieName;
    private String orderId;
    private double price;
    private String screeningHall;
    private int status;
    private int userId;

    public Record(int amount, String beginTime, String cinemaName, long createTime, String endTime, int id, String movieName, String orderId, double price, String screeningHall, int status, int userId) {
        this.amount = amount;
        this.beginTime = beginTime;
        this.cinemaName = cinemaName;
        this.createTime = createTime;
        this.endTime = endTime;
        this.id = id;
        this.movieName = movieName;
        this.orderId = orderId;
        this.price = price;
        this.screeningHall = screeningHall;
        this.status = status;
        this.userId = userId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getScreeningHall() {
        return screeningHall;
    }

    public void setScreeningHall(String screeningHall) {
        this.screeningHall = screeningHall;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
