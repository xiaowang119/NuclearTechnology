package module5;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.nucleartechnology.R;

/**
 * Created by admin on 2017/7/20.
 */

public class RaditionAccidentLevel extends AppCompatActivity implements View.OnClickListener{

    private ActionBar actionBar;
    private LinearLayout linearLayout1,linearLayout2,linearLayout3, linearLayout4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module5_radition_accident_level);
        actionBar = getSupportActionBar();
        actionBar.setTitle("事故分级");

        linearLayout1 = (LinearLayout) findViewById(R.id.module5LinearLayout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.module5LinearLayout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.module5LinearLayout3);
        linearLayout4 = (LinearLayout) findViewById(R.id.module5LinearLayout4);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.module5ArrowDown1:
                if (linearLayout1.getVisibility() == View.VISIBLE)
                    linearLayout1.setVisibility(View.GONE);
                else
                    linearLayout1.setVisibility(View.VISIBLE);
                break;
            case R.id.module5ArrowDown2:
                if (linearLayout2.getVisibility() == View.VISIBLE)
                    linearLayout2.setVisibility(View.GONE);
                else
                    linearLayout2.setVisibility(View.VISIBLE);
                break;
            case R.id.module5ArrowDown3:
                if (linearLayout3.getVisibility() == View.VISIBLE)
                    linearLayout3.setVisibility(View.GONE);
                else
                    linearLayout3.setVisibility(View.VISIBLE);
                break;
            case R.id.module5ArrowDown4:
                if (linearLayout4.getVisibility() == View.VISIBLE)
                    linearLayout4.setVisibility(View.GONE);
                else
                    linearLayout4.setVisibility(View.VISIBLE);
                break;

        }
    }
}
