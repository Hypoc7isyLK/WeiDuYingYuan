package zmz.zhao.com.zmz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bw.movie.R;

import zmz.zhao.com.zmz.activity.ChooseActivity;


public class MovieFragment extends BaseFragment {
    private Button tiao;

    @Override
    public void initView(View view) {
        tiao = view.findViewById(R.id.tiao);
        tiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ChooseActivity.class));

            }
        });
    }

    @Override
    public void initData(View view) {

    }

    @Override
    public int getContent() {
        return R.layout.activity_movie;
    }


}
