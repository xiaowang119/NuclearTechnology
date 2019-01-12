package module5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nucleartechnology.R;

/**
 * Created by 白海涛 on 2017/7/21.
 */

public class RaditionExample extends AppCompatActivity {

    private ActionBar actionBar;
    private ListView listView;
    private String[] strings = {"特别重大辐射事故", "重大辐射事故", "较大辐射事故", "一般辐射事故"};
    private String[] address = {"file:///android_asset/辐射事故_特别重大事故.htm", "file:///android_asset/辐射事故_重大事故.htm"
            , "file:///android_asset/辐射事故_较大事故.htm", "file:///android_asset/辐射事故_一般事故.htm"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module5_radition_example);
        actionBar = getSupportActionBar();
        actionBar.setTitle("典型案列");
        listView = (ListView) findViewById(R.id.raditionAccidentExamples);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.module5_radition_item, strings);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        NuclearElecBasicKnowledgeItem.onActivityStart(RaditionExample.this, address[0], strings[0]);
                        break;
                    case 1:
                        NuclearElecBasicKnowledgeItem.onActivityStart(RaditionExample.this, address[1], strings[1]);
                        break;
                    case 2:
                        NuclearElecBasicKnowledgeItem.onActivityStart(RaditionExample.this, address[2], strings[2]);
                        break;
                    case 3:
                        NuclearElecBasicKnowledgeItem.onActivityStart(RaditionExample.this, address[3], strings[3]);
                        break;

                }
            }
        });
    }
}
