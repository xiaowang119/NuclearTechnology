package module3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;


import com.example.nucleartechnology.R;

/**
 * Created by 白海涛 on 2017/7/17.
 */

public class LawsEnforceAssistantMain extends AppCompatActivity implements View.OnClickListener{

    private ActionBar actionBar;
    private LinearLayout linearLayoutHelper1,linearLayoutHelper2,linearLayoutHelper3,linearLayoutHelper4,linearLayoutHelper5,
            linearLayoutHelper6,linearLayoutHelper7,linearLayoutHelper8,linearLayoutHelper9, linearLayoutHelper10;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module3_lawshelper_main);
        actionBar = getSupportActionBar();
        actionBar.setTitle("执法助手");

        linearLayoutHelper1 = (LinearLayout) findViewById(R.id.linearLayoutHelper1);
        linearLayoutHelper2 = (LinearLayout) findViewById(R.id.linearLayoutHelper2);
        linearLayoutHelper3 = (LinearLayout) findViewById(R.id.linearLayoutHelper3);
        linearLayoutHelper4 = (LinearLayout) findViewById(R.id.linearLayoutHelper4);
        linearLayoutHelper5 = (LinearLayout) findViewById(R.id.linearLayoutHelper5);
        linearLayoutHelper6 = (LinearLayout) findViewById(R.id.linearLayoutHelper6);
        linearLayoutHelper7 = (LinearLayout) findViewById(R.id.linearLayoutHelper7);
        linearLayoutHelper8 = (LinearLayout) findViewById(R.id.linearLayoutHelper8);
        linearLayoutHelper9 = (LinearLayout) findViewById(R.id.linearLayoutHelper9);
        linearLayoutHelper10 = (LinearLayout) findViewById(R.id.linearLayoutHelper10);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.arrowDownButtonHelper1:
                if (linearLayoutHelper1.getVisibility() == View.VISIBLE) {
                    linearLayoutHelper1.setVisibility(View.GONE);
                } else {
                    linearLayoutHelper1.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.arrowDownButtonHelper2:
                if (linearLayoutHelper2.getVisibility() == View.VISIBLE) {
                    linearLayoutHelper2.setVisibility(View.GONE);
                } else {
                    linearLayoutHelper2.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.arrowDownButtonHelper3:
                if (linearLayoutHelper3.getVisibility() == View.VISIBLE) {
                    linearLayoutHelper3.setVisibility(View.GONE);
                } else {
                    linearLayoutHelper3.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.arrowDownButtonHelper4:
                if (linearLayoutHelper4.getVisibility() == View.VISIBLE) {
                    linearLayoutHelper4.setVisibility(View.GONE);
                } else {
                    linearLayoutHelper4.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.arrowDownButtonHelper5:
                if (linearLayoutHelper5.getVisibility() == View.VISIBLE) {
                    linearLayoutHelper5.setVisibility(View.GONE);
                } else {
                    linearLayoutHelper5.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.arrowDownButtonHelper6:
                if (linearLayoutHelper6.getVisibility() == View.VISIBLE) {
                    linearLayoutHelper6.setVisibility(View.GONE);
                } else {
                    linearLayoutHelper6.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.arrowDownButtonHelper7:
                if (linearLayoutHelper7.getVisibility() == View.VISIBLE) {
                    linearLayoutHelper7.setVisibility(View.GONE);
                } else {
                    linearLayoutHelper7.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.arrowDownButtonHelper8:
                if (linearLayoutHelper8.getVisibility() == View.VISIBLE) {
                    linearLayoutHelper8.setVisibility(View.GONE);
                } else {
                    linearLayoutHelper8.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.arrowDownButtonHelper9:
                if (linearLayoutHelper9.getVisibility() == View.VISIBLE) {
                    linearLayoutHelper9.setVisibility(View.GONE);
                } else {
                    linearLayoutHelper9.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.arrowDownButtonHelper10:
                if (linearLayoutHelper10.getVisibility() == View.VISIBLE) {
                    linearLayoutHelper10.setVisibility(View.GONE);
                } else {
                    linearLayoutHelper10.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }
}
