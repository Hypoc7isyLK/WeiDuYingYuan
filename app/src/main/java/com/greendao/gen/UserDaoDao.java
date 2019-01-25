package com.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import zmz.zhao.com.zmz.bean.dao.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_DAO".
*/
public class UserDaoDao extends AbstractDao<UserDao, Void> {

    public static final String TABLENAME = "USER_DAO";

    /**
     * Properties of entity UserDao.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property SessionId = new Property(0, String.class, "sessionId", false, "SESSION_ID");
        public final static Property UserId = new Property(1, int.class, "userId", false, "USER_ID");
        public final static Property Birthday = new Property(2, String.class, "birthday", false, "BIRTHDAY");
        public final static Property HeadPic = new Property(3, String.class, "headPic", false, "HEAD_PIC");
        public final static Property Id = new Property(4, String.class, "id", false, "ID");
        public final static Property LastLoginTime = new Property(5, String.class, "lastLoginTime", false, "LAST_LOGIN_TIME");
        public final static Property NickName = new Property(6, String.class, "nickName", false, "NICK_NAME");
        public final static Property Phone = new Property(7, String.class, "phone", false, "PHONE");
        public final static Property Sex = new Property(8, String.class, "sex", false, "SEX");
        public final static Property Flag = new Property(9, boolean.class, "flag", false, "FLAG");
        public final static Property Login_flag = new Property(10, boolean.class, "login_flag", false, "LOGIN_FLAG");
        public final static Property Pwd = new Property(11, String.class, "pwd", false, "PWD");
        public final static Property Status = new Property(12, int.class, "status", false, "STATUS");
    }


    public UserDaoDao(DaoConfig config) {
        super(config);
    }
    
    public UserDaoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_DAO\" (" + //
                "\"SESSION_ID\" TEXT," + // 0: sessionId
                "\"USER_ID\" INTEGER NOT NULL ," + // 1: userId
                "\"BIRTHDAY\" TEXT," + // 2: birthday
                "\"HEAD_PIC\" TEXT," + // 3: headPic
                "\"ID\" TEXT," + // 4: id
                "\"LAST_LOGIN_TIME\" TEXT," + // 5: lastLoginTime
                "\"NICK_NAME\" TEXT," + // 6: nickName
                "\"PHONE\" TEXT," + // 7: phone
                "\"SEX\" TEXT," + // 8: sex
                "\"FLAG\" INTEGER NOT NULL ," + // 9: flag
                "\"LOGIN_FLAG\" INTEGER NOT NULL ," + // 10: login_flag
                "\"PWD\" TEXT," + // 11: pwd
                "\"STATUS\" INTEGER NOT NULL );"); // 12: status
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_DAO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserDao entity) {
        stmt.clearBindings();
 
        String sessionId = entity.getSessionId();
        if (sessionId != null) {
            stmt.bindString(1, sessionId);
        }
        stmt.bindLong(2, entity.getUserId());
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(3, birthday);
        }
 
        String headPic = entity.getHeadPic();
        if (headPic != null) {
            stmt.bindString(4, headPic);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(5, id);
        }
 
        String lastLoginTime = entity.getLastLoginTime();
        if (lastLoginTime != null) {
            stmt.bindString(6, lastLoginTime);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(7, nickName);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(8, phone);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(9, sex);
        }
        stmt.bindLong(10, entity.getFlag() ? 1L: 0L);
        stmt.bindLong(11, entity.getLogin_flag() ? 1L: 0L);
 
        String pwd = entity.getPwd();
        if (pwd != null) {
            stmt.bindString(12, pwd);
        }
        stmt.bindLong(13, entity.getStatus());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserDao entity) {
        stmt.clearBindings();
 
        String sessionId = entity.getSessionId();
        if (sessionId != null) {
            stmt.bindString(1, sessionId);
        }
        stmt.bindLong(2, entity.getUserId());
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(3, birthday);
        }
 
        String headPic = entity.getHeadPic();
        if (headPic != null) {
            stmt.bindString(4, headPic);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(5, id);
        }
 
        String lastLoginTime = entity.getLastLoginTime();
        if (lastLoginTime != null) {
            stmt.bindString(6, lastLoginTime);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(7, nickName);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(8, phone);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(9, sex);
        }
        stmt.bindLong(10, entity.getFlag() ? 1L: 0L);
        stmt.bindLong(11, entity.getLogin_flag() ? 1L: 0L);
 
        String pwd = entity.getPwd();
        if (pwd != null) {
            stmt.bindString(12, pwd);
        }
        stmt.bindLong(13, entity.getStatus());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public UserDao readEntity(Cursor cursor, int offset) {
        UserDao entity = new UserDao( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // sessionId
            cursor.getInt(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // birthday
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // headPic
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // id
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // lastLoginTime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // nickName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // phone
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // sex
            cursor.getShort(offset + 9) != 0, // flag
            cursor.getShort(offset + 10) != 0, // login_flag
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // pwd
            cursor.getInt(offset + 12) // status
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserDao entity, int offset) {
        entity.setSessionId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUserId(cursor.getInt(offset + 1));
        entity.setBirthday(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setHeadPic(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLastLoginTime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setNickName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPhone(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSex(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setFlag(cursor.getShort(offset + 9) != 0);
        entity.setLogin_flag(cursor.getShort(offset + 10) != 0);
        entity.setPwd(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setStatus(cursor.getInt(offset + 12));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(UserDao entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(UserDao entity) {
        return null;
    }

    @Override
    public boolean hasKey(UserDao entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
