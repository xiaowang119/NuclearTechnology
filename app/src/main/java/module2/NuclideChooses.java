package module2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.nucleartechnology.MainActivity;
import com.example.nucleartechnology.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by 白海涛 on 2017/6/28.
 */

public class NuclideChooses extends AppCompatActivity {

    private SQLiteDatabase database;
    private String DATA_PATH ="/data/data/com.example.nucleartechnology/databases"+"/mulu.db";
    ListView lv;
    SearchView search;
    ActionBar actionBar;

    private ArrayList<HashMap<String, String>> listDataFromENSDF = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.module1_radionuclide);
        actionBar = getSupportActionBar();
        actionBar.setTitle("放射性核素查询");
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        lv = (ListView) findViewById(R.id.lv_nuclides);
        search = (SearchView) findViewById(R.id.search);
        search.setIconifiedByDefault(false);
        search.setSubmitButtonEnabled(true);
        search.setQueryHint("支持元素名及衰变时长查询");

        ENSDFdataAddToList();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(Main8Activity.this,query,Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    lv.clearTextFilter();
                } else {
                    lv.setFilterText(newText);
                }
                return true;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Intent intent = new Intent();
                    Bundle data = new Bundle();
                    HashMap<String, String> hashMap = (HashMap<String, String>) parent.getItemAtPosition(position);
                    data.putString("name",hashMap.get("element_symbol"));
                    data.putString("sequence",hashMap.get("element_mass"));
                    intent.putExtras(data);
                    setResult(0,intent);
                    finish();

                }
            }
        });
        //HashMap<String , String> map = new HashMap<>();


    }

    public void ENSDFdataAddToList() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("element_symbol", "元素符号");
        hashMap.put("element_mass", "质量数");
        listDataFromENSDF.add(hashMap);

        for (int i = 0; i < MainActivity.listDecays.size(); i++) {
            HashMap<String, String> hashMap1 = new HashMap<>();
            hashMap1.put("element_symbol", MainActivity.listDecays.elementAt(i).getParentName());
            hashMap1.put("element_mass", MainActivity.listDecays.elementAt(i).getParentMass());
            listDataFromENSDF.add(hashMap1);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listDataFromENSDF, R.layout.simple_item_element_detail,
                new String[]{"element_symbol", "element_mass"}, new int[]{R.id.lv_part1, R.id.lv_part2});
        lv.setAdapter(simpleAdapter);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.isCheckable())
        {
            item.setCheckable(true);
        }
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Toast toast = Toast.makeText(NuclideChooses.this, "请选择待计算核素并自动返回", Toast.LENGTH_SHORT);
            toast.show();}
        return true;
    }
}

