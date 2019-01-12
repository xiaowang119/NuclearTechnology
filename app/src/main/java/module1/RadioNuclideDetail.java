package module1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;


import com.example.nucleartechnology.MainActivity;
import com.example.nucleartechnology.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import ensdfparser.Query;
import ensdfparser.SonNuclide;
import ensdfparser.StructDecay;

import static ensdfparser.QueryTypeByNuclide.NAME_PARENT;


/**
 * Created by admin on 2017/6/28.
 */

public class RadioNuclideDetail extends AppCompatActivity {

    ListView lv_alpha;
    ListView lv_belta;
    ListView lv_gamma;
    ListView lv_elec;
    TextView tv_1;
    TextView tv_2;
    TextView tv_3;
    TextView tv_4;
    TextView tv_5;
    TextView tv_duxing;

    List<Map<String,String>> list1 = new ArrayList<Map<String,String>>();
    List<Map<String,String>> list2 = new ArrayList<Map<String,String>>();
    List<Map<String,String>> list3 = new ArrayList<Map<String,String>>();
    List<Map<String,String>> list4 = new ArrayList<Map<String,String>>();
    ActionBar actionBar;


    private Query query;
    private Vector<StructDecay> resultDecays;
    private StructDecay queryResult;
    private Vector<SonNuclide> sonNuclides;
    private TextView ziHe,halfLife;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module1_radionuclide_detail);

        Intent intent = getIntent();
        String elementSymbol = intent.getStringExtra("element_symbol");
        String elementMass = intent.getStringExtra("element_mass");

        actionBar = getSupportActionBar();
        actionBar.setTitle(elementSymbol + "-" + elementMass + "查询");

        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("alpha", null).setContent(R.id.tab1));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("beta-", null).setContent(R.id.tab2));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("gamma", null).setContent(R.id.tab3));
        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("beta+", null).setContent(R.id.tab4));
        lv_alpha = (ListView) findViewById(R.id.lv_alpha);
        lv_belta = (ListView) findViewById(R.id.lv_belta);
        lv_gamma = (ListView) findViewById(R.id.lv_gamma);
        lv_elec = (ListView) findViewById(R.id.lv_elec);
        tv_1 = (TextView) findViewById(R.id.ac9_tv_1);
        tv_2 = (TextView) findViewById(R.id.ac9_tv_2);
        tv_3 = (TextView) findViewById(R.id.ac9_tv_3);
        tv_4 = (TextView) findViewById(R.id.ac9_tv_4);
        tv_5 = (TextView) findViewById(R.id.ac9_tv_5);
        tv_duxing = (TextView) findViewById(R.id.duxing);
        ziHe = (TextView) findViewById(R.id.ziHeName);
        halfLife = (TextView) findViewById(R.id.halfLife);


        query = new Query(MainActivity.listDecays);
        resultDecays = query.findByNuclide(NAME_PARENT, elementSymbol);
        for (int i = 0; i < resultDecays.size(); i++) {
            if (resultDecays.elementAt(i).getParentMass().equals(elementMass)) {
                queryResult = resultDecays.elementAt(i);
                sonNuclides = queryResult.getSoNuclides();
            }
        }
        String ziheNameString = "";
        String ziheHalfString = "";
        for (int i = 0; i < sonNuclides.size(); i++) {
            if (i == 0) {
                ziheNameString = ziheNameString  + sonNuclides.elementAt(i).getSonName();
                ziheHalfString = ziheHalfString  + sonNuclides.elementAt(i).getHalfLife();
            }
            if (i >= 1) {
                ziheNameString = ziheNameString + "|" + sonNuclides.elementAt(i).getSonName();
                ziheHalfString = ziheHalfString + "|" + sonNuclides.elementAt(i).getHalfLife();
            }
        }
        ziHe.setText(ziheNameString);
        halfLife.setText(ziheHalfString);

        if (sonNuclides.size() != 0) {
            Map<String, String> title = new HashMap<String, String>();
            title.put("type", "衰变方式");
            title.put("energy", "能量（KeV）");
            title.put("intensity", "发射几率");
            list1.add(title);
            list2.add(title);
            list3.add(title);
            list4.add(title);
            for (int i = 0; i < sonNuclides.size(); i++) {
                Vector<String> alphaEnergy = sonNuclides.elementAt(i).getAlphaEnergyVector();
                Vector<String> alphaInsity = sonNuclides.elementAt(i).getAlphaIntensityVector();
                Vector<String> betaEnergy = sonNuclides.elementAt(i).getBetaEnergyVector();
                Vector<String> betaInsity = sonNuclides.elementAt(i).getBetaIntenistyVector();
                Vector<String> gammaEnergy = sonNuclides.elementAt(i).getGammaEnergyVector();
                Vector<String> gammaInsity = sonNuclides.elementAt(i).getGammaIntensityVector();
                Vector<String> postiveBetaEnergy = sonNuclides.elementAt(i).getPostiveBetaEnergyVector();
                Vector<String> postiveBetaInsity = sonNuclides.elementAt(i).getPostiveBetaIntensityVector();
                if (alphaInsity.size() != 0 ) {
                    for (int j = 0; j < alphaEnergy.size(); j++) {
                        Map<String, String> map = new HashMap<>();
                        map.put("type", sonNuclides.elementAt(i).getDecayTypeString());
                        map.put("energy", alphaEnergy.elementAt(j));
                        map.put("intensity", alphaInsity.elementAt(j));
                        Log.e("tiaoshitest", "alphaE: " + alphaEnergy.elementAt(j));
                        Log.e("tiaoshitest", "alphaI: " + alphaInsity.elementAt(j));
                        list1.add(map);
                    }
                }
                if (betaInsity.size() != 0) {
                    for (int j = 0; j < betaEnergy.size(); j++) {
                        Map<String, String> map = new HashMap<>();
                        map.put("type", sonNuclides.elementAt(i).getDecayTypeString());
                        map.put("energy", betaEnergy.elementAt(j));
                        map.put("intensity", betaInsity.elementAt(j));
                        Log.e("tiaoshitest", "betaE: " + betaEnergy.elementAt(j));
                        Log.e("tiaoshitest", "betaI: " + betaInsity.elementAt(j));
                        list2.add(map);
                    }
                }
                if (gammaInsity.size() != 0) {
                    for (int j = 0; j < gammaEnergy.size(); j++) {
                        Map<String, String> map = new HashMap<>();
                        map.put("type", sonNuclides.elementAt(i).getDecayTypeString());
                        map.put("energy", gammaEnergy.elementAt(j));
                        map.put("intensity", gammaInsity.elementAt(j));
                        list3.add(map);
                    }
                }
                if (postiveBetaInsity.size() != 0) {
                    for (int j = 0; j < postiveBetaEnergy.size(); j++) {
                        Map<String, String> map = new HashMap<>();
                        map.put("type", sonNuclides.elementAt(i).getDecayTypeString());
                        map.put("energy", postiveBetaEnergy.elementAt(j));
                        map.put("intensity", postiveBetaInsity.elementAt(j));
                        list4.add(map);
                    }
                }
            }
            SimpleAdapter alpha_Adapter = new SimpleAdapter(this, list1, R.layout.simple_item_radionuclide_detail,
                    new String[]{"type", "energy", "intensity"}, new int[]{R.id.lv_item_part3, R.id.lv_item_part1, R.id.lv_item_part2});
            lv_alpha.setAdapter(alpha_Adapter);
            SimpleAdapter belta_Adapter = new SimpleAdapter(this, list2, R.layout.simple_item_radionuclide_detail,
                    new String[]{"type", "energy", "intensity"}, new int[]{R.id.lv_item_part3, R.id.lv_item_part1, R.id.lv_item_part2});
            lv_belta.setAdapter(belta_Adapter);
            SimpleAdapter gamma_Adapter = new SimpleAdapter(this, list3, R.layout.simple_item_radionuclide_detail,
                    new String[]{"type", "energy", "intensity"}, new int[]{R.id.lv_item_part3, R.id.lv_item_part1, R.id.lv_item_part2});
            lv_gamma.setAdapter(gamma_Adapter);
            SimpleAdapter elec_Adapter = new SimpleAdapter(this, list4, R.layout.simple_item_radionuclide_detail,
                    new String[]{"type", "energy", "intensity"}, new int[]{R.id.lv_item_part3, R.id.lv_item_part1, R.id.lv_item_part2});
            lv_elec.setAdapter(elec_Adapter);
        }


        String duXing = queryResult.getGroup();
        String level1 = queryResult.getLevel1();
        String level2 = queryResult.getLevel2();
        String level3 = queryResult.getLevel3();
        String level4 = queryResult.getLevel4();
        String level5 = queryResult.getLevel5();
        if (!TextUtils.isEmpty(duXing)) {
            tv_duxing.setText(duXing);
        } else {
            tv_duxing.setText("信息缺失");
        }
        if (!TextUtils.isEmpty(level1)) {
            tv_1.setText(level1);
        } else {
            tv_1.setText("暂无");
        }
        if (!TextUtils.isEmpty(level2)) {
            tv_2.setText(level2);
        } else {
            tv_2.setText("暂无");
        }
        if (!TextUtils.isEmpty(level3)) {
            tv_3.setText(level3);
        } else {
            tv_3.setText("暂无");
        }
        if (!TextUtils.isEmpty(level4)) {
            tv_4.setText(level4);
        } else {
            tv_4.setText("暂无");
        }
        if (!TextUtils.isEmpty(level5)) {
            tv_5.setText(level5);
        } else {
            tv_5.setText("暂无");
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
