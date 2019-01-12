package module1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.example.nucleartechnology.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.sql.*;



/**
 * Created by admin on 2017/6/28.
 */

public class ElementDetail extends AppCompatActivity {

    private String[] basic_info = new String[]{"原子序数","相对原子质量","元素中文名","元素英文名"};
    private String[] state_info = new String[]{"元素状态","来源","密度(g/m^3)","电离能(eV)"};

    List<Map<String,Object>> listItems1=new ArrayList<Map<String, Object>>();
    List<Map<String,Object>> listItems2=new ArrayList<Map<String, Object>>();
    List<Map<String,Object>> listItems3=new ArrayList<Map<String, Object>>();
    private SQLiteDatabase database;
    private String DATA_PATH = "/data/data/com.example.nucleartechnology/databases/element.db";

    private int sequence;

    ListView lv_basic;
    ListView lv_state;
    ListView lv_abundance;
    ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module1_element_detail);
        Bundle bundle = this.getIntent().getExtras();
        sequence = bundle.getInt("sequence");
        Log.e("test","当前页面显示第"+sequence+"号元素信息");
        lv_basic = (ListView)findViewById(R.id.lv_basic);
        lv_abundance = (ListView)findViewById(R.id.lv_abundance);
        lv_state = (ListView)findViewById(R.id.lv_state);

        actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);



        load_element();
        SimpleAdapter simpleAdapter1 = new SimpleAdapter(this,listItems1,R.layout.simple_item_element_detail,new String[]{"desc","data"},new int[]{R.id.lv_part1,R.id.lv_part2});
        SimpleAdapter simpleAdapter2 = new SimpleAdapter(this,listItems2,R.layout.simple_item_element_detail,new String[]{"desc","data"},new int[]{R.id.lv_part1,R.id.lv_part2});
        SimpleAdapter simpleAdapter3 = new SimpleAdapter(this,listItems3,R.layout.simple_item_element_detail,new String[]{"desc","data"},new int[]{R.id.lv_part1,R.id.lv_part2});
        lv_basic.setAdapter(simpleAdapter1);
        lv_state.setAdapter(simpleAdapter2);
        lv_abundance.setAdapter(simpleAdapter3);

    }

    private void load_element(){
        try{
            database = SQLiteDatabase.openOrCreateDatabase(DATA_PATH,null);
            String[] element = new String[4];
            String[] state_data = new String[4];
            String name;
            Cursor cursor = database.rawQuery("select * from element where elem_z like ?",new String[]{String.valueOf(sequence)});
            int count = cursor.getCount();
            Log.e("test","周期表查询记录数量"+count);
            cursor.moveToFirst();
            do{
                name = cursor.getString(0);
                actionBar.setTitle("                                                            "+name+"    元素详细信息");
                element[0] = cursor.getString(1);
                element[1] = cursor.getString(2);
                element[2] = cursor.getString(3);
                element[3] = cursor.getString(4);
                for(int i=0;i<4;i++)
                {
                    Map<String,Object> listItem=new HashMap<String,Object>();
                    listItem.put("desc",basic_info[i]);
                    listItem.put("data",element[i]);
                    listItems1.add(listItem);
                }

                state_data[0] = cursor.getString(5);
                state_data[1] = cursor.getString(6);
                state_data[2] = cursor.getString(7);
                state_data[3] = cursor.getString(8);
                for(int i=0;i<4;i++)
                {
                    Map<String,Object> listItem=new HashMap<String,Object>();
                    listItem.put("desc",state_info[i]);
                    listItem.put("data",state_data[i]);
                    listItems2.add(listItem);
                }
            }while (cursor.moveToNext());

            cursor = database.rawQuery("select * from abundance where elem_z like ?",new String[]{name});
            count = cursor.getCount();
            Log.e("test","丰度表查询记录数量"+count);
            if(count!=0)
            {
                cursor.moveToFirst();
                for(int i=0;i<count;i++)
                {
                    Map<String,Object> listItem=new HashMap<String,Object>();
                    listItem.put("desc",cursor.getString(1));
                    listItem.put("data",cursor.getString(2));
                    listItems3.add(listItem);
                    Log.e("test","插入日志"+i);
                    cursor.moveToNext();
                }
            }
            cursor.close();
            database.close();
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
}
