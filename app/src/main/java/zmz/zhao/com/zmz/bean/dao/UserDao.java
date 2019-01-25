package zmz.zhao.com.zmz.bean.dao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * date:2019/1/23
 * author:李阔(淡意衬优柔)
 * function:
 */
@Entity
public class UserDao {

    @Property(nameInDb = "SESSIONID")
    private String sessionId;

    @Property(nameInDb = "USERID")
    private int userId;

    @Property(nameInDb = "BIRTHDAY")
    private String birthday;

    @Property(nameInDb = "HEADPIC")
    private String headPic;

    @Property(nameInDb = "ID")
    private String id;

    @Property(nameInDb = "LASTLOGINTIME")
    private String lastLoginTime;

    @Property(nameInDb = "NICKNAME")
    private String nickName;

    @Property(nameInDb = "PHONE")
    private String phone;

    @Property(nameInDb = "SEX")
    private String sex;
    private boolean flag;
    private boolean login_flag;
    private String pwd;
    @Generated(hash = 240748791)
    public UserDao(String sessionId, int userId, String birthday, String headPic,
            String id, String lastLoginTime, String nickName, String phone,
            String sex, boolean flag, boolean login_flag, String pwd) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.birthday = birthday;
        this.headPic = headPic;
        this.id = id;
        this.lastLoginTime = lastLoginTime;
        this.nickName = nickName;
        this.phone = phone;
        this.sex = sex;
        this.flag = flag;
        this.login_flag = login_flag;
        this.pwd = pwd;
    }
    @Generated(hash = 917059161)
    public UserDao() {
    }
    public String getSessionId() {
        return this.sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getHeadPic() {
        return this.headPic;
    }
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLastLoginTime() {
        return this.lastLoginTime;
    }
    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public boolean getFlag() {
        return this.flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public boolean getLogin_flag() {
        return this.login_flag;
    }
    public void setLogin_flag(boolean login_flag) {
        this.login_flag = login_flag;
    }
    public String getPwd() {
        return this.pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


}
