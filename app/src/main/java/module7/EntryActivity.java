package module7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nucleartechnology.R;

import static module7.ReviewAdapter.NUM;

public class EntryActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        ActivityCollector.addActivity(this);//创建一个新活动时加入活动管理器
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,2);
        TextView textStandard1 = (TextView) findViewById(R.id.textStandard1);
        TextView textMeasure1 = (TextView) findViewById(R.id.textMeasure1);
        TextView textBox = (TextView) findViewById(R.id.textBox);
        TextView textInspection = (TextView) findViewById(R.id.textIns);
        TextView textBill = (TextView) findViewById(R.id.textBil);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //查询表中所有数据
        Cursor cursor = db.query("Book",null,null,null,null,null,null);
        if(cursor.moveToPosition(NUM)){
            //遍历Cursor对象，取出数据并打印
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String author = cursor.getString(cursor.getColumnIndex("author"));
            String pages = cursor.getString(cursor.getColumnIndex("pages"));
            String page = cursor.getString(cursor.getColumnIndex("page"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            textStandard1.setText(name);
            textMeasure1.setText(author);
            textBox.setText(pages);
            textInspection.setText(page);
            textBill.setText(price);
        }
        cursor.close();
    }
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            String action = intent.getAction();
            if(action.equals("again_login")){
                Toast.makeText(EntryActivity.this,"账号在别处登陆,已强制下线",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(EntryActivity.this,LoginActivity.class);
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
}