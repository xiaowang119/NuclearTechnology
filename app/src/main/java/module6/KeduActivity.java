package module6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nucleartechnology.R;

/**
 * 设置刻度系数
 * Created by 白海涛 on 2017/9/19.
 */

public class KeduActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private EditText eta, etb;
    private Button no, yes;

    double a, b;

    private CheckBox rememberKedu;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module6_kedu);
        actionBar = getSupportActionBar();
        actionBar.setTitle("刻度剂量系数");

        eta = (EditText) findViewById(R.id.xishu_a);
        etb = (EditText) findViewById(R.id.jieju_b);
        no = (Button) findViewById(R.id.noKedu);
        yes = (Button) findViewById(R.id.yesKedu);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        rememberKedu = (CheckBox) findViewById(R.id.remember_kedu);
        boolean isRemember = pref.getBoolean("remember", false);
        if (isRemember) {
            String xielv = pref.getString("xielv", "");
            String jieju = pref.getString("jieju", "");
            eta.setText(xielv);
            etb.setText(jieju);
            rememberKedu.setChecked(true);
        }


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sa = eta.getText().toString();
                String sb = etb.getText().toString();
                if (sa.equals("") || sb.equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入刻度系数", Toast.LENGTH_SHORT).show();
                } else {
                    editor = pref.edit();
                    if (rememberKedu.isChecked()) {
                        editor.putBoolean("remember", true);
                        editor.putString("xielv", sa);
                        editor.putString("jieju", sb);
                    } else {
                        editor.clear();
                    }
                    editor.apply();


                    Bundle bundle = new Bundle();
                    a = Double.parseDouble(sa);
                    b = Double.parseDouble(sb);
                    bundle.putDouble("a", a);
                    bundle.putDouble("b", b);
                    bundle.putBoolean("isKedu",true);
                    Intent intent = new Intent(getApplicationContext(), CameraMainActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putDouble("a", 0);
                bundle.putDouble("b", 0);
                bundle.putBoolean("isKedu", false);
                Intent intent = new Intent(getApplicationContext(), CameraMainActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }




}
