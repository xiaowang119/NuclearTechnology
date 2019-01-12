package module7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.nucleartechnology.R;

public class ChemistryActivity extends AppCompatActivity {

    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemistry);
        ActivityCollector.addActivity(this);//创建一个新活动时加入活动管理器
        //实例化WebView对象
        webview = (WebView) findViewById(R.id.webView);
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        try {
            //设置打开的页面地址
            webview.loadUrl("file:///android_asset/index.html");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            String action = intent.getAction();
            if(action.equals("again_login")){
                Toast.makeText(ChemistryActivity.this,"账号在别处登陆,已强制下线",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(ChemistryActivity.this,LoginActivity.class);
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