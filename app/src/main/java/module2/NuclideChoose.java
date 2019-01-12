package module2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
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
 * Created by admin on 2017/6/28.
 */

public class NuclideChoose extends AppCompatActivity {
    private SQLiteDatabase database;
    private String DATA_PATH ="/data/data/com.example.nucleartechnology/databases"+"/mulu.db";
    List<Map<String,String>> list = new ArrayList<Map<String,String>>();
    ListView lv;
    SearchView search;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module2_nuclidechoose);
        lv = (ListView)findViewById(R.id.lv_nuclides_ac11);
        search  =(SearchView)findViewById(R.id.search_ac11);
        search.setIconifiedByDefault(false);
        search.setSubmitButtonEnabled(true);
        search.setQueryHint("支持元素名及衰变时长查询");

        actionBar=getSupportActionBar();
        actionBar.setTitle("放射性同位素");
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        importDataBase();
        updateListView();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    lv.clearTextFilter();
                }else{
                    lv.setFilterText(newText);
                }
                return true;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap item = (HashMap)parent.getItemAtPosition(position);
                String section = String.valueOf(item.get("title").toString());
                String[] query = null;
                query = section.split("-");
                Intent intent = getIntent();
                Bundle data = new Bundle();
                Log.e("name",query[0]);
                Log.e("sequence",query[1]);
                data.putString("name",query[0]);
                data.putString("sequence",query[1]);
                intent.putExtras(data);
                NuclideChoose.this.setResult(0,intent);
                NuclideChoose.this.finish();
            }
        });
    }

    public void importDataBase(){
        String dirPath = "/data/data/com.example.nucleartechnology/databases";
        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file = new File(dir,"record.db");
        try{
            if(!file.exists()) {
                file.createNewFile();
                InputStream is = this.getResources().openRawResource(R.raw.record);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                fos.write(buffer);
                is.close();
                fos.close();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        File file1 = new File(dir,"mulu.db");
        try{
            if(!file1.exists()) {
                file1.createNewFile();
                InputStream is = this.getResources().openRawResource(R.raw.mulu);
                FileOutputStream fos = new FileOutputStream(file1);
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                fos.write(buffer);
                is.close();
                fos.close();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateListView(){
        try{
            database = SQLiteDatabase.openOrCreateDatabase(DATA_PATH,null);
            Cursor cursor = database.rawQuery("select * from dir",null);
            int count =cursor.getCount();
            int i=0;
            String title[] = new String[count];
            String zihe[] = new String[count];
            String halflife[] = new String[count];

            cursor.moveToFirst();
            do{
                title[i] = cursor.getString(1);
                zihe[i]  = cursor.getString(2);
                halflife[i] = cursor.getString(3);
                i++;
            }while (cursor.moveToNext());
            cursor.close();
            database.close();

            Map<String, String> head = new HashMap<String, String>();
            head.put("title","核素");
            //head.put("z","子核");
            head.put("Halflife","半衰期");
            list.add(head);

            for(i=0;i<title.length;i++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("title",title[i]);
                //map.put("z",zihe[i]);
                map.put("Halflife",halflife[i]);
                list.add(map);
            }

            SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,R.layout.simple_item_element_detail,
                    new String[]{"title","Halflife"},new int[]{R.id.lv_part1,R.id.lv_part2});
            lv.setAdapter(simpleAdapter);
        }catch (SQLiteException e){
            e.printStackTrace();
        }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Toast toast = Toast.makeText(NuclideChoose.this, "请选择待计算核素并自动返回", Toast.LENGTH_SHORT);
            toast.show();}
        return true;
    }
}
