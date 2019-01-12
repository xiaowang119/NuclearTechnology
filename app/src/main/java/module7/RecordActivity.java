package module7;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nucleartechnology.R;

import java.util.ArrayList;
import java.util.List;

import static module7.EssentialActivity.da;
import static module7.MonitorActivity.A1;
import static module7.MonitorActivity.A2;
import static module7.MonitorActivity.A3;
import static module7.MonitorActivity.A4;
import static module7.MonitorActivity.A5;


public class RecordActivity extends AppCompatActivity {

    private List<CheckList> checkListList = new ArrayList<>();
    private MyDatabaseHelper dbHelper;
    String receive_da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ActivityCollector.addActivity(this);//创建一个新活动时加入活动管理器
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,2);
        dbHelper.getWritableDatabase();
        if(da == null){
        }else {
            receive_da = da;
            initCheckLists();
        }
        //给每一个textView显示出报警的相关内容
        TextView textSta = (TextView)findViewById(R.id.textSta);
        textSta.setText(A1);
        TextView textMea = (TextView)findViewById(R.id.textMea);
        textMea.setText(A2);
        TextView textBox = (TextView)findViewById(R.id.textBox);
        textBox.setText(A3);
        TextView textIns = (TextView)findViewById(R.id.textIns);
        textIns.setText(A4);
        TextView textBil = (TextView)findViewById(R.id.textBil);
        textBil.setText(A5);
        //给listView添加报警事件
        CheckListAdapter adapter = new CheckListAdapter(RecordActivity.this,R.layout.checklist_item,checkListList);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        Button finish = (Button) findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一条数据
                values.put("name",A1);
                values.put("author",A2);
                values.put("pages",A3);
                values.put("page",A4);
                values.put("price",A5);
                db.insert("Book",null,values);
                values.clear();
                Intent intent = new Intent(RecordActivity.this,MonitorActivity.class);
                startActivity(intent);
            }
        });
    }
    //添加相应的报警符合事件
    private void initCheckLists(){
        for (int i=0; i<1; i++){
            if(receive_da.equals("OK")){
                //第一类结果登记
                CheckList checkList1 = new CheckList("*.误报警");
                checkListList.add(checkList1);
            }else if(receive_da.equals("NOT")){
                CheckList checkList1 = new CheckList("*.进口货物放射性污染");
                checkListList.add(checkList1);
            }else if(!receive_da.equals("OK") && !receive_da.equals("NOT")){
                //第二类结果登记
                if(receive_da.substring(0,1).equals("A") || receive_da.substring(1,2).equals("B") || receive_da.substring(2,3).equals("C") || receive_da.substring(3,4).equals("D")){
                    CheckList checkList1 = new CheckList("*.不排除涉恐嫌疑");
                    checkListList.add(checkList1);
                    if(receive_da.substring(0,1).equals("A")){
                        CheckList checkList2 = new CheckList("      无合法手续或无法说明正当用途的放射源或和材料");
                        checkListList.add(checkList2);
                    }
                    if(receive_da.substring(1,2).equals("B")){
                        CheckList checkList3 = new CheckList("      以隐蔽方式企图偷运入境的放射源或核材料");
                        checkListList.add(checkList3);
                    }
                    if(receive_da.substring(2,3).equals("C")){
                        CheckList checkList4 = new CheckList("      不明物质的放射源泄露或扩散，造成环境或物品污染");
                        checkListList.add(checkList4);
                    }
                    if(receive_da.substring(3,4).equals("D")){
                        CheckList checkList5 = new CheckList("      获取相关核辐射恐怖袭击信息或有证据表明恐怖分子将实施核辐射恐怖袭击信息");
                        checkListList.add(checkList5);
                    }
                }
                //第三类结果登记
                if(receive_da.substring(4,5).equals("E") || receive_da.substring(5,6).equals("F") || receive_da.substring(6,7).equals("G") || receive_da.substring(7,8).equals("H")){
                    CheckList checkList6 = new CheckList("*.可能的辐射事故");
                    checkListList.add(checkList6);
                    if(receive_da.substring(4,5).equals("E")){
                        CheckList checkList7 = new CheckList("      合法进口的放射源丢失，被盗，失控");
                        checkListList.add(checkList7);
                    }
                    if(receive_da.substring(5,6).equals("F")){
                        CheckList checkList8 = new CheckList("      合法进口的放射性同位素和射线装置失控导致人员受到超过年辐射剂量的限值的照射或疾病，残疾，死亡的");
                        checkListList.add(checkList8);
                    }
                    if(receive_da.substring(6,7).equals("G")){
                        CheckList checkList9 = new CheckList("      放射性物质泄漏，造成放射性环境污染");
                        checkListList.add(checkList9);
                    }

                }
                //第五类结果登记
                if(receive_da.substring(7,8).equals("H") || receive_da.substring(8,9).equals("I") || receive_da.substring(9,10).equals("J") || receive_da.substring(10,11).equals("K")){
                    CheckList checkList10 = new CheckList("*.一般放射性超标事件");
                    checkListList.add(checkList10);
                    if(receive_da.substring(7,8).equals("H")){
                        CheckList checkList11 = new CheckList("      有证据表明入境旅客因接受过放射性药物检查或者介入放射性治疗");
                        checkListList.add(checkList11);
                    }
                    if(receive_da.substring(8,9).equals("I")){
                        CheckList checkList12 = new CheckList("      入境旅客携带的荧光表，指南针等日常生活用品");
                        checkListList.add(checkList12);
                    }
                    if(receive_da.substring(9,10).equals("J")){
                        CheckList checkList13 = new CheckList("      旅客携带有少量有明确用途的矿石，添加剂，装饰用品等");
                        checkListList.add(checkList13);
                    }
                    if(receive_da.substring(10,11).equals("K")){
                        CheckList checkList14 = new CheckList("      其他一般放射性超标，可以排除涉恐嫌疑，辐射事故可能和超过国家标准的放射性污染的");
                        checkListList.add(checkList14);
                    }
                }
            }
        }
    }
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            String action = intent.getAction();
            if(action.equals("again_login")){
                Toast.makeText(RecordActivity.this,"账号在别处登陆,已强制下线",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(RecordActivity.this,LoginActivity.class);
                startActivity(intent1);
            }
            finish();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.record, menu);
        return true;
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.aa:
                Toast.makeText(this, "目前暂无具体功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bb:
                Toast.makeText(this, "目前暂无具体功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cc:
                Toast.makeText(this, "目前暂无具体功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dd:
                Toast.makeText(this, "目前暂无具体功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ee:
                Toast.makeText(this, "目前暂无具体功能", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

}