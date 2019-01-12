package com.example.nucleartechnology;

/**
 * Created by lenovo on 2017/12/3.
 */



import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

import ensdfparser.DataBaseOperate;
import ensdfparser.StructDecay;
import module5.NuclearAccidentMain;

public class MainActivity extends AppCompatActivity implements GridView.OnItemClickListener {

    private DataBaseOperate dataBaseOperate;
    public static Vector<StructDecay> listDecays;
    SQLiteDatabase database2;
    private String DATA_PATH2 = "/data/data/com.example.nucleartechnology/databases/data.db";


    private int[] imagArr = new int[]{
            R.drawable.icon_element, R.drawable.icon_radiation_element,
            R.drawable.icon_radiation, R.drawable.icon_radiation_level,
            R.drawable.icon_calculation, R.drawable.icon_shield,
            R.drawable.icon_law, R.drawable.icon_implement,
            R.drawable.icon_radiation_monitoring, R.drawable.icon_chemistry,
            R.drawable.icon_radiation_accident, R.drawable.icon_nuclear_emergency,
            R.drawable.icon_phone_radiation
    };

    private String[] titleArr;
    ArrayList<Bean> dataList = new ArrayList<>();
    Adapter mAdapter;
    GridView gridView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridView);
        titleArr = getResources().getStringArray(R.array.item_arr);
        gridView.setOnItemClickListener(this);
        gridView.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < imagArr.length + 2; i++) {
                    Bean bean = new Bean();
                    if (i > imagArr.length - 1) {
                        bean.imageId = 0;
                        bean.title = "";
                    } else {
                        bean.imageId = imagArr[i];
                        bean.title = titleArr[i];
                    }

                    dataList.add(bean);
                }
                mAdapter = new Adapter(MainActivity.this, dataList);
                gridView.setAdapter(mAdapter);
            }
        });

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("载入数据中，请稍后^_^");
        progressDialog.setMessage("核数据读取中...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(){
            public void run(){
                // 加载用户数据
                // 载入吸收系数文件需要查询的数据库文件；
                loadAbsorpFile();
                loadSqlDataFile();
                //载入ENSDF数据库；
                loadENSDFDatabase();
                loadDataFromEDSDFParser();

                progressDialog.dismiss();
            }
        }.start();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Bean data = (Bean) adapterView.getItemAtPosition(i);
        Intent intent;
        switch (i) {
            case 0:
                intent = new Intent(MainActivity.this, module1.TableOfElements.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(MainActivity.this, module1.RadioNuclide.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(MainActivity.this, module1.RadioUnits.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(MainActivity.this, module1.RadioLevel.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(MainActivity.this, module2.DecayCalculation.class);
                startActivity(intent);
                break;
            case 5:
                intent = new Intent(MainActivity.this, module2.ShieldAndDoseCalculation.class);
                startActivity(intent);
                break;

            case 6:
                intent = new Intent(MainActivity.this, module3.LawsMainActivity.class);
                startActivity(intent);
                break;
            case 7:
                intent = new Intent(MainActivity.this, module3.LawsEnforceAssistantMain.class);
                startActivity(intent);
                break;
            case 8:
                intent = new Intent(MainActivity.this, module4.RaditionMoniteMainActivity.class);
                startActivity(intent);
                break;
            case 9:
                intent = new Intent(getApplicationContext(), module7.ChemistryActivity.class);
                startActivity(intent);
                break;
            case 10:
                intent = new Intent(getApplicationContext(), module5.RaditionAccidentMain.class);
                startActivity(intent);
                break;
            case 11:
                intent = new Intent(getApplicationContext(), NuclearAccidentMain.class);
                startActivity(intent);
                break;
            case 12:
                intent = new Intent(getApplicationContext(), module7.LoginActivity.class);
                startActivity(intent);
        }

    }

    private void loadDataFromEDSDFParser() {
        dataBaseOperate = new DataBaseOperate(DATA_PATH2, null, false);
        database2 = SQLiteDatabase.openOrCreateDatabase(DATA_PATH2,null);
        Cursor cursor = database2.rawQuery("select * from decaymessage", null);
        Log.d("test", "Cursor:行数： " + cursor.getCount());
        Log.d("test", "Cursor:列数： " + cursor.getColumnCount());

        listDecays = dataBaseOperate.getAll();
        Log.d("test", "查询数据源大小： "+listDecays.size());
    }


    private void loadSqlDataFile() {
        String dirPath = "/data/data/com.example.nucleartechnology/databases";
        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file = new File(dir,"element.db");
        try{
            if(!file.exists()) {
                file.createNewFile();
                InputStream is = this.getResources().openRawResource(R.raw.element);
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
    }

    private void loadENSDFDatabase() {
        String dirPath = "/data/data/com.example.nucleartechnology/databases";
        String s = Environment.getExternalStorageDirectory().getAbsolutePath();
        //String dirPath = s + "/DataBases";
        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(dir,"data.db");
        try{
            if(!file.exists()) {
                file.createNewFile();
                InputStream is = this.getResources().openRawResource(R.raw.data);
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
        Log.d("test", "loadENSDFDatabase: ");
    }



    private void loadAbsorpFile() {
        String dirPath = "/data/data/com.example.nucleartechnology/param";
        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file = new File(dir,"absorptparam.def");
        try{
            if(!file.exists()) {
                file.createNewFile();
                InputStream is = this.getResources().openRawResource(R.raw.absorptparam);
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

        file = new File(dir,"attenparam.def");
        try{
            if(!file.exists()) {
                file.createNewFile();
                InputStream is = this.getResources().openRawResource(R.raw.attenparam);
                FileOutputStream fos = new FileOutputStream(file);
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
}

