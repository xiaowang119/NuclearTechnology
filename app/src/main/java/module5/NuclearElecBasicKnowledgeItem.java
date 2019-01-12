package module5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.nucleartechnology.R;

/**
 * Created by 白海涛 on 2017/7/21.
 */

public class NuclearElecBasicKnowledgeItem extends AppCompatActivity {

    private WebView webView;
    private ActionBar actionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String htmlAddress = intent.getStringExtra("html_address");
        String title = intent.getStringExtra("title");
        actionBar = getSupportActionBar();
        actionBar.setTitle(title);

        //实例化WebView对象
        webView = new WebView(this);
        //设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webView.loadUrl(htmlAddress);
        //设置Web视图
        setContentView(webView);


    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
//            webView.goBack();
//            return true;
//        }
//        return false;
//    }

    public static void onActivityStart(Context context, String string,String title) {
        Intent intent = new Intent(context, NuclearElecBasicKnowledgeItem.class);
        intent.putExtra("title", title);
        intent.putExtra("html_address", string);
        context.startActivity(intent);
    }
}
