package module5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.nucleartechnology.R;

/**
 * Created by 白海涛 on 2017/7/21.
 */

public class RaditionReport extends AppCompatActivity implements View.OnClickListener {


    private ActionBar actionBar;
    private LinearLayout linearLayout1,linearLayout2,linearLayout3, linearLayout4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module5_radition_report);
        actionBar = getSupportActionBar();
        actionBar.setTitle("报告责任");

        linearLayout1 = (LinearLayout) findViewById(R.id.report1);
        linearLayout2 = (LinearLayout) findViewById(R.id.report2);
        linearLayout3 = (LinearLayout) findViewById(R.id.report3);
        linearLayout4 = (LinearLayout) findViewById(R.id.report4);

    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.reportArrowDown1:
                if (linearLayout1.getVisibility() == View.VISIBLE)
                    linearLayout1.setVisibility(View.GONE);
                else
                    linearLayout1.setVisibility(View.VISIBLE);
                break;
            case R.id.reportArrowDown2:
                if (linearLayout2.getVisibility() == View.VISIBLE)
                    linearLayout2.setVisibility(View.GONE);
                else
                    linearLayout2.setVisibility(View.VISIBLE);
                break;
            case R.id.reportArrowDown3:
                if (linearLayout3.getVisibility() == View.VISIBLE)
                    linearLayout3.setVisibility(View.GONE);
                else
                    linearLayout3.setVisibility(View.VISIBLE);
                break;
            case R.id.reportArrowDown4:
                if (linearLayout4.getVisibility() == View.VISIBLE)
                    linearLayout4.setVisibility(View.GONE);
                else
                    linearLayout4.setVisibility(View.VISIBLE);
                break;

        }
    }
}
