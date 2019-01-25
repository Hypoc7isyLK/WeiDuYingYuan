package zmz.zhao.com.zmz.util;

import com.greendao.gen.UserDaoDao;

import java.util.List;

import zmz.zhao.com.zmz.app.MyApplication;
import zmz.zhao.com.zmz.bean.dao.UserDao;

/**
 * date:2019/1/24 15:39
 * author:赵明珠(啊哈)
 * function:
 */
public class DaoUtils {

    static UserDaoDao userDaoDao = MyApplication.getInstances().getDaoSession().getUserDaoDao();

    static List<UserDao> userDaos = userDaoDao.loadAll();

    public static int USERID(){

        if (userDaos != null && userDaos.size() > 0){
            UserDao userDao = userDaos.get(userDaos.size()-1);
            int userId = userDao.getUserId();
            return userId;
        }


        return 0;
    }
    public static String SessionId(){
        if (userDaos != null && userDaos.size() > 0){
            UserDao userDao = userDaos.get(userDaos.size()-1);
            String sessionId = userDao.getSessionId();
            return sessionId;
        }
        return "";
    }
    public static boolean Flag(){
        if (userDaos != null && userDaos.size() > 0){
            UserDao userDao = userDaos.get(userDaos.size()-1);
            boolean flag = userDao.getFlag();
            return flag;
        }
        return false;
    }
    public static boolean LoginFlag(){
        if (userDaos != null && userDaos.size() > 0){
            UserDao userDao = userDaos.get(userDaos.size()-1);
            boolean login_flag = userDao.getLogin_flag();
            return login_flag;
        }
        return false;
    }
    public static String PWD(){
        if (userDaos != null && userDaos.size() > 0){
            UserDao userDao = userDaos.get(userDaos.size()-1);
            String pwd = userDao.getPwd();
            return pwd;
        }
        return "";
    }
    public static String PHONE(){
        if (userDaos != null && userDaos.size() > 0){
            UserDao userDao = userDaos.get(userDaos.size()-1);
            String phone = userDao.getPhone();
            return phone;
        }
        return "";
    }
}
