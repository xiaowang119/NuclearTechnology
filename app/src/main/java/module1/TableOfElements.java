package module1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.nucleartechnology.R;

/**
 * Created by admin on 2017/6/28.
 */

public class TableOfElements extends AppCompatActivity {

    PTofElememt pt_element;
    Button bt_check;
    private int sequence;
    ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module1_table_element);
        actionBar = getSupportActionBar();
        actionBar.hide();
        pt_element = (PTofElememt) findViewById(R.id.pt_table);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float width=dm.widthPixels;
        float height=dm.heightPixels;
        float w=1920;float h=1080;
        pt_element.setScale_x(w/width);
        pt_element.setScale_y(h/height);
        bt_check = (Button)findViewById(R.id.bt_check);
        sequence = pt_element.seleted_element;
        bt_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data=new Bundle();
                Log.e("test","窗口传值"+pt_element.seleted_element);
                data.putInt("sequence",pt_element.seleted_element);
                Intent intent=new Intent(TableOfElements.this,ElementDetail.class);
                intent.putExtras(data);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
}
