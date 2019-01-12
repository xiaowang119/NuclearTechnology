package module7;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.example.nucleartechnology.R;
import java.util.ArrayList;
import java.util.List;

import static module7.LoginActivity.list_num;


public class ItemActivity extends AppCompatActivity {

    private List<Review> reviewList = new ArrayList<>();
    int listNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        ActivityCollector.addActivity(this);//创建一个新活动时加入活动管理其
        list_num++;
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ReviewAdapter adapter = new ReviewAdapter(reviewList);
        recyclerView.setAdapter(adapter);
        reviewList.clear();


        initReviews();

    }

    private void initReviews(){

        for(int j=1; j<list_num; j++){
            Review review = new Review("报警记录"+j);
            reviewList.add(review);
        }

    }
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            String action = intent.getAction();
            if(action.equals("again_login")){
                Toast.makeText(ItemActivity.this,"账号在别处登陆,已强制下线",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(ItemActivity.this,LoginActivity.class);
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