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
            UserDao userDao = userDaos.get(0);
            int userId = userDao.getUserId();
            return userId;
        }


        return 4634;
    }
    public static String SessionId(){
        if (userDaos != null && userDaos.size() > 0){
            UserDao userDao = userDaos.get(0);
            String sessionId = userDao.getSessionId();
            return sessionId;
        }
        return "";
    }

}
