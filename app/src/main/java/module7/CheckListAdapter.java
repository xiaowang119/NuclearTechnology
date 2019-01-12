package module7;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nucleartechnology.R;

import org.w3c.dom.Text;

import java.util.List;

public class CheckListAdapter extends ArrayAdapter<CheckList>{

    private int resourceId;

    public CheckListAdapter(Context context, int textViewResourceId,
                            List<CheckList> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View converView, ViewGroup parent){
        CheckList checkList = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView checkCorritor = (TextView) view.findViewById(R.id.check_corritor);
        checkCorritor.setText(checkList.getText());
        return view;
    }
}
