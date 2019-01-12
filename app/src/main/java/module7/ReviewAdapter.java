package module7;


import android.content.Context;
import android.content.Intent;
import android.icu.text.AlphabeticIndex;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nucleartechnology.R;

import java.util.List;

import static module7.LoginActivity.list_num;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{

    private List<Review> mReviewList;
    private Context mContext;

    public static int NUM;


    static class ViewHolder extends RecyclerView.ViewHolder{
        View reviewView;
        TextView reviewName;
        Button reviewButton;

        public ViewHolder(View view){
            super(view);
            reviewView = view;
            reviewName = (TextView) view.findViewById(R.id.review_name);
            reviewButton = (Button) view.findViewById(R.id.review_button);
        }
    }

    public ReviewAdapter(List<Review> reviewList){
        mReviewList = reviewList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if( mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.reviewView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//便写点击复核记录的代码
                int position  = holder.getAdapterPosition();
                Review review = mReviewList.get(position);
                NUM = position;
                Intent intent = new Intent(mContext,EntryActivity.class);
                mContext.startActivity(intent);

            }
        });
        holder.reviewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //写入复核事件的代码
                Intent intent = new Intent(mContext,EssentialActivity.class);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Review review = mReviewList.get(position);
        holder.reviewName.setText(review.getName());
        if(position < list_num-2){
            holder.reviewButton.setText("已复核");
        }else {
            holder.reviewButton.setText("复核");
        }

    }

    @Override
    public int getItemCount(){
        return mReviewList.size();
    }
}