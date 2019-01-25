package zmz.zhao.com.zmz.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greendao.gen.DaoMaster;
import com.greendao.gen.UserDaoDao;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import zmz.zhao.com.zmz.bean.dao.UserDao;

/**
 * date:2019/1/22
 * author:李阔(Hypoc7isy语涩)
 * function:
 */
public abstract class BaseFragment extends Fragment {


    private Unbinder unbinder;
    public UserDao USERDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getContent(), container, false);
        unbinder = ButterKnife.bind(this, view);
        UserDaoDao userDaoDao = DaoMaster.newDevSession(getActivity(), UserDaoDao.TABLENAME).getUserDaoDao();

        List<UserDao> userDaoList = userDaoDao.queryBuilder().where(UserDaoDao.Properties.Status.eq(1)).list();

        if (userDaoList != null && userDaoList.size()>0) {
            USERDAO = userDaoList.get(0);
        }

        initView(view);
        initData(view);
        return view;
    }
    public abstract int getContent();
    public abstract void initView(View view);
    public abstract void initData(View view);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
