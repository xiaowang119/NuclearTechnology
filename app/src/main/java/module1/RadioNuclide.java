package module1;

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

public class RadioNuclide extends AppCompatActivity {

    private SQLiteDatabase database;
    private String DATA_PATH ="/data/data/com.example.nucleartechnology/databases"+"/mulu.db";
    List<Map<String,String>> list = new ArrayList<Map<String,String>>();
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


        lv = (ListView) findViewById(R.id.lv_nuclides);
        search = (SearchView) findViewById(R.id.search);
        search.setIconifiedByDefault(false);
        search.setSubmitButtonEnabled(true);
        search.setQueryHint("支持元素名及衰变时长查询");

        ENSDFdataAddToList();


        //importDataBase();
        //updateListView();

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

//新数据库用的
                    Intent intent = new Intent(RadioNuclide.this, module1.RadioNuclideDetail.class);
                    HashMap<String, String> hashMap = (HashMap<String, String>) parent.getItemAtPosition(position);
                    intent.putExtra("element_symbol", hashMap.get("element_symbol"));
                    intent.putExtra("element_mass", hashMap.get("element_mass"));
                    startActivity(intent);

//                    HashMap item = (HashMap) parent.getItemAtPosition(position);
//                    String section = String.valueOf(item.get("title").toString());
//                    String section1 = String.valueOf(item.get("Halflife").toString());
//                    String[] query = null;
//                    String[] query1 = null;
//                    query = section.split("-");
//                    query1 = section1.split(" ");
//                    Intent intent = new Intent();
//                    Bundle data = new Bundle();
//                    data.putString("name", query[0]);
//                    data.putString("sequence", query[1]);
//                    data.putString("halflife", query1[0]);
//                    data.putString("unit", query1[1]);
//                    intent.setClass(RadioNuclide.this, module1.RadioNuclideDetail.class);
//                    intent.putExtras(data);
//                    startActivity(intent);
                }
            }
        });


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
            //String zihe[] = new String[count];
            String halflife[] = new String[count];

            cursor.moveToFirst();
            do{
                title[i] = cursor.getString(1);
                //zihe[i]  = cursor.getString(2);
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

    /*
    public void updateListView(){
        try{
            database = SQLiteDatabase.openOrCreateDatabase(DATA_PATH,null);
            Cursor cursor = database.rawQuery("select * from Radardec4OL",null);
            int count =cursor.getCount();
            int i=0;
            String A[] = new String[count];
            String ELEM[] = new String[count];
            String Halflife[] = new String[count];
            String Units[] = new String[count];
            String Z[] = new String[count];

            cursor.moveToFirst();
            do {
                A[i] = cursor.getString(0);
                ELEM[i] = cursor.getString(1);
                Z[i] = cursor.getString(2);
                Halflife[i] =  cursor.getString(4);
                Units[i]= cursor.getString(5);
                i++;
            }while (cursor.moveToNext());
            cursor.close();
            database.close();

            ArrayList<String> list_A = new ArrayList<String>();
            ArrayList<String> list_ELEM = new ArrayList<String>();
            ArrayList<String> list_Halflife = new ArrayList<String>();
            ArrayList<String> list_Units = new ArrayList<String>();

            for(int j =0;j<A.length;j++){
                if(!list_A.contains(ELEM[j]+"-"+A[j])){
                    list_A.add(ELEM[j]+"-"+A[j]);
                    list_ELEM.add(Z[j]);
                    list_Halflife.add(Halflife[j]);
                    list_Units.add(Units[j]);
                }
            }

            A = list_A.toArray((new String[list_A.size()]));
            ELEM = list_ELEM.toArray((new String[list_ELEM.size()]));
            Halflife = list_Halflife.toArray((new String[list_Halflife.size()]));
            Units = list_Units.toArray((new String[list_Units.size()]));

            Map<String, String> title = new HashMap<String, String>();
            title.put("title","核素");
            title.put("z","子核");
            title.put("Halflife","半衰期");
            list.add(title);

            for(i=0;i<A.length;i++) {
                Map<String, String> map = new HashMap<String, String>();
                //map.put("title",ELEM[i]+"-"+A[i]);
                map.put("title",A[i]);
                map.put("z",ELEM[i]);
                map.put("Halflife",Halflife[i]+" "+Units[i]);
                list.add(map);
            }

            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.admin.diy/databases"+"/mulu.db",null);
            db.execSQL("create table dir(_id integer primary key autoincrement,title varchar(10),zihe integer,halflife varchar(20))");
            for(int k=0;k<A.length;k++)
            {
                db.execSQL("insert into dir values(null,?,?,?)",new String[]{A[k],ELEM[k],Halflife[k]+" "+Units[k]});
            }
            db.close();

            SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,R.layout.simple_item3,
                    new String[]{"title","z","Halflife"},new int[]{R.id.lv_item_part3,R.id.lv_item_part1,R.id.lv_item_part2});
            lv.setAdapter(simpleAdapter);
        }catch (SQLiteException se){
            se.printStackTrace();
        }
    }
    */


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

