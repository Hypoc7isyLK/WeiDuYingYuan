package zmz.zhao.com.zmz.bean;

import org.greenrobot.greendao.annotation.Entity;

import zmz.zhao.com.zmz.bean.dao.UserDao;

/**
 * date:2019/1/23
 * author:李阔(淡意衬优柔)
 * function:
 */

public class LoginBean {

    private String sessionId;
    private int userId;
    private UserDao userInfo;

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

    public UserDao getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserDao userInfo) {
        this.userInfo = userInfo;
    }
}
