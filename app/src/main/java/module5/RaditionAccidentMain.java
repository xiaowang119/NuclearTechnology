package module5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.nucleartechnology.R;

/**
 * Created by 白海涛 on 2017/7/20.
 */

public class RaditionAccidentMain extends AppCompatActivity implements View.OnClickListener {

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module5_radition_main);
        actionBar = getSupportActionBar();
        actionBar.setTitle("辐射事故应急");


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        switch (id) {
            case R.id.level:
                intent = new Intent(getApplicationContext(), RaditionAccidentLevel.class);
                startActivity(intent);
                break;
            case R.id.report:
                intent = new Intent(getApplicationContext(), RaditionReport.class);
                startActivity(intent);
                break;
            case R.id.example:
                intent = new Intent(getApplicationContext(), RaditionExample.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
