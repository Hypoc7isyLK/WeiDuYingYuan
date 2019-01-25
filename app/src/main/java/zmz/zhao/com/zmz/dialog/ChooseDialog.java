package zmz.zhao.com.zmz.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;

/**
 * date:2019/1/25
 * author:李阔(淡意衬优柔)
 * function:
 */
public class ChooseDialog extends DialogFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(),R.layout.choose_pay_dialog,null);

        //初始化资源控件
        initView(view);

        //初始化数据
        initData();
        return view;
    }

    private void initData() {

    }

    private void initView(View view) {

    }

    @Override
    public void onClick(View v) {

    }
}
