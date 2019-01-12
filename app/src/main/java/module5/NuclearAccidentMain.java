package module5;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.nucleartechnology.R;

/**
 * Created by 白海涛 on 2017/7/21.
 */

public class NuclearAccidentMain extends AppCompatActivity implements View.OnClickListener {

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module5_nuclear_main);
        actionBar = getSupportActionBar();
        actionBar.setTitle("核事故应急");

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        switch (id) {
            case R.id.basicKnowledge:
                intent = new Intent(getApplicationContext(),NuclearElecBasicKnowledge.class);
                startActivity(intent);
                break;
            case R.id.nuclearAccidentLevel:

                break;
            case R.id.reportItem:
                NuclearElecBasicKnowledgeItem.onActivityStart(NuclearAccidentMain.this,"file:///android_asset/核事故应急报告制度.html","核事故报告制度");                break;
            case R.id.responseMeasure:
                intent = new Intent(getApplicationContext(),NuclearAccidentResponse.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
