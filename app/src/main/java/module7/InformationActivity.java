package module7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.nucleartechnology.R;



public class InformationActivity extends AppCompatActivity {

    TextView NbMo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ActivityCollector.addActivity(this);//创建一个新活动时加入活动管理器
        NbMo = (TextView)findViewById(R.id.nb_mo);
        String a = getIntent().getStringExtra("out_data");
        if(a==null){
            NbMo.setText("您无权查看该条报警记录或最近无报警记录");
        }else {
            NbMo.setText(a);
        }

    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}

