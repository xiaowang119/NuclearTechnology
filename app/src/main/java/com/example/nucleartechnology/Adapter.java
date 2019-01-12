package com.example.nucleartechnology;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-11-22.
 */
public class Adapter extends BaseAdapter {

    Context mContext;
    ArrayList<Bean> mList;
    LayoutInflater inflater;
    public Adapter(Context context, ArrayList<Bean> list) {
        this.mList=list;
        this.mContext=context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Bean data= (Bean) getItem(i);
        ViewHolder holder;
        if (view==null){
            holder=new ViewHolder();
            view=inflater.inflate(R.layout.item_grid_view_layout,null);
            holder.iv= (ImageView) view.findViewById(R.id.icon);
            holder.title=(TextView)view.findViewById(R.id.name);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        if (data.imageId!=0) {
            holder.iv.setImageResource(data.imageId);
            holder.title.setText(data.title);
        }
        return view;
    }
    class ViewHolder{
        ImageView iv;
        TextView title;
    }
}
