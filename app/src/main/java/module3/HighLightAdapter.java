package module3;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.nucleartechnology.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 显示搜索结果
 * 搜索结果的关键搜索词进行高亮显示；
 * Created by 白海涛 on 2017/5/29.
 */

public class HighLightAdapter extends BaseAdapter {

    private Context context =null;
    private ArrayList<HashMap<String, Object>> lawsLis = null;
    private String searchWord = null;

    public HighLightAdapter(Context context, ArrayList<HashMap<String, Object>> lawsLis, String searchWord) {
        this.context = context;
        this.lawsLis = lawsLis;
        this.searchWord = searchWord;
    }

    @Override
    public int getCount() {
        return lawsLis.size();
    }

    @Override
    public Object getItem(int position) {
        return lawsLis.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //取出法律法规的名称，简介数据；
        String name = lawsLis.get(position).get("name").toString();
        String digest = lawsLis.get(position).get("digest").toString();

        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.laws_item1, null);
            viewHolder.name = (TextView) view.findViewById(R.id.law_name);
            viewHolder.digest = (TextView) view.findViewById(R.id.law_digest);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        SpannableStringBuilder styleName = new SpannableStringBuilder(name);
        SpannableStringBuilder styleDigest = new SpannableStringBuilder(digest);

        if (name.contains(searchWord)) {
            int start = name.indexOf(searchWord) ;
            int end = start + searchWord.length();
            styleName.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            viewHolder.name.setText(styleName);
        } else {
            viewHolder.name.setText(name);
        }

        String restDigest = digest;
        if (restDigest.contains(searchWord)) {
            int posStart = 0;
            int posEnd = 0;
            do {
                int start = restDigest.indexOf(searchWord);
                posStart = posStart + start;
                posEnd = posStart + searchWord.length();
                styleDigest.setSpan(new ForegroundColorSpan(Color.RED), posStart, posEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                restDigest = restDigest.substring(start + searchWord.length());
                posStart = posStart + searchWord.length();
            } while (restDigest.contains(searchWord));

            viewHolder.digest.setText(styleDigest);
        } else {
            viewHolder.digest.setText(digest);
        }

        return view;
    }

    class ViewHolder {
        public TextView name;
        public TextView digest;
    }


}
