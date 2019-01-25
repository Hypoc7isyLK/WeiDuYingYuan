package zmz.zhao.com.zmz.activity;

import android.os.Bundle;

import com.bw.movie.R;
import com.qfdqc.views.seattable.SeatTable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseActivity extends BaseActivity {


    @BindView(R.id.choose_cinema_settable)
    SeatTable chooseCinemaSettable;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        chooseCinemaSettable.setSeatChecker(new SeatTable.SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int column) {
                if(column==2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if(row==6&&column==6){
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return new String[0];
            }
        });
        chooseCinemaSettable.setData(10,15);

    }

    @Override
    protected void destoryData() {

    }


    @OnClick(R.id.choose_cinema_settable)
    public void onViewClicked() {
        chooseCinemaSettable.setScreenName("8号厅荧幕");//设置屏幕名称
        chooseCinemaSettable.setMaxSelected(3);//设置最多选中

    }

}
