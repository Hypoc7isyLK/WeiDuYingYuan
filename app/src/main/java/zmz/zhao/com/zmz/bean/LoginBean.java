package zmz.zhao.com.zmz.bean;

import zmz.zhao.com.zmz.bean.dao.UserInfo;

/**
 * date:2019/1/23
 * author:李阔(淡意衬优柔)
 * function:
 */

public class LoginBean {

    private String sessionId;
    private int userId;
    private UserInfo userInfo;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
