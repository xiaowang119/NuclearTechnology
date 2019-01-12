package module7;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.nucleartechnology.R;
import java.util.List;
import static module7.MSG.TYPE_RECEIVED;
import static module7.MSG.TYPE_SEND;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<MSG> mMsgList;

    static public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;

        public ViewHolder(View View) {
            super(View);
            leftLayout = (LinearLayout) itemView.findViewById(R.id.layout_left);
            rightLayout = (LinearLayout) itemView.findViewById(R.id.layout_right);
            leftMsg = (TextView) itemView.findViewById(R.id.left_msg);
            rightMsg = (TextView) itemView.findViewById(R.id.right_msg);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MSG msg = mMsgList.get(position);
        if (msg.getType() == TYPE_RECEIVED) {
            //如果收到的消息,则显示左边的消息布局,将右边的消息布局隐藏
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
        } else if (msg.getType() == TYPE_SEND) {
            //如果是发出的消息,则显示右边的消息布局,将左边的消息布局隐藏
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

    public MsgAdapter(List<MSG> mMsgList) {
        this.mMsgList = mMsgList;
    }
}