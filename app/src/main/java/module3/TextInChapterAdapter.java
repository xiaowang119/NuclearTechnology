package module3;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.nucleartechnology.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * Created by 白海涛 on 2017/6/1.
 */

public class TextInChapterAdapter extends BaseAdapter {

    private Context context = null;
    private ArrayList<HashMap<String, Object>> textInChapter = null;
    private String searchPharse = null;
    private boolean[] showControl; // 用一个布尔数组记录list中每个item是否要展开

    ViewHolder viewHolder;

    public TextInChapterAdapter(Context context, ArrayList<HashMap<String, Object>> textInChapter, String searchPharse) {
        this.context = context;
        this.textInChapter = textInChapter;
        this.searchPharse = searchPharse;
        showControl = new boolean[textInChapter.size()];
    }

    @Override
    public int getCount() {
        return textInChapter.size();
    }

    @Override
    public long getItemId(int position) {
        //return position;
        return 0;
    }

    @Override
    public Object getItem(int position) {
        //return textInChapter.get(position);
        return null;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //取出数据源
        String chapter_name = textInChapter.get(position).get("Chapter").toString();
        String chapter_text = textInChapter.get(position).get("TextInChapter").toString();

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.laws_item2, null);
            viewHolder.chapterName = (TextView) convertView.findViewById(R.id.chapter_name);
            viewHolder.textInChapter = (TextView) convertView.findViewById(R.id.textInChapter);
            viewHolder.show = (LinearLayout) convertView.findViewById(R.id.showArea);
            viewHolder.hide = (LinearLayout) convertView.findViewById(R.id.hideArea);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final SpannableStringBuilder styleName = new SpannableStringBuilder(chapter_name);
        SpannableStringBuilder styleText = new SpannableStringBuilder(chapter_text);
        if (searchPharse.equals("")) {
            viewHolder.chapterName.setText(chapter_name);
        } else if (chapter_name.contains(searchPharse)) {
            int start = chapter_name.indexOf(searchPharse);
            int end = start + searchPharse.length();
            styleName.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            viewHolder.chapterName.setText(styleName);
        } else {
            viewHolder.chapterName.setText(chapter_name);
        }

        String text = chapter_text;
        if (searchPharse.equals("")) {
            viewHolder.textInChapter.setText(chapter_text);
        } else if (text.contains(searchPharse)) {
            int posStart = 0;
            int posEnd = 0;
            do {
                int start = text.indexOf(searchPharse);
                posStart = posStart + start;
                posEnd = posStart + searchPharse.length();
                styleText.setSpan(new ForegroundColorSpan(Color.RED), posStart, posEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                text = text.substring(start + searchPharse.length());
                posStart = posStart + searchPharse.length();
            } while (text.contains(searchPharse));
            viewHolder.textInChapter.setText(styleText);
        } else {
            viewHolder.textInChapter.setText(chapter_text);

        }

        viewHolder.show.setTag(position);
        if (showControl[position]) {
            viewHolder.hide.setVisibility(View.GONE);
        } else {
            viewHolder.hide.setVisibility(View.VISIBLE);
        }


        viewHolder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (Integer) v.getTag();
                if (showControl[tag]) {
                    showControl[tag] = false;
                } else {
                    showControl[tag] = true;
                }
                //通知adapter数据改变需要重新加载
                notifyDataSetChanged(); //必须要有一步
            }
        });
        return convertView;
    }

    class ViewHolder {
        public TextView chapterName;
        public TextView textInChapter;
        public LinearLayout show,hide;
    }



}
