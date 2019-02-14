package zmz.zhao.com.zmz.bean;

/**
 * @author dingtao
 * @date 2018/12/28 10:05
 * qq:1940870847
 */
public class Result<T> {

    private String status;
    private String message;
    private T result;
    private T orderList;
    private String headPath;
    private String orderId;
    private int flag;
    private String downloadUrl;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public T getOrderList() {
        return orderList;
    }

    public void setOrderList(T orderList) {
        this.orderList = orderList;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
