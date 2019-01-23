package zmz.zhao.com.zmz.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.OnCheckedChanged;


public class FocusActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
    }
    @OnCheckedChanged(R.id.mine_radio)
    public void OnClickChanged(RadioGroup group, int checkedId){
        if (checkedId == R.id.mine_movie){

        }
    }

}
