package zmz.zhao.com.zmz.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * date:2019/1/22
 * author:李阔(Hypoc7isy语涩)
 * function:
 */
public abstract class BaseFragment extends Fragment {


    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getContent(), container, false);
        unbinder = ButterKnife.bind(this, view);

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
