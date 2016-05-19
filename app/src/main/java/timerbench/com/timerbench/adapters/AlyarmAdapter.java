package timerbench.com.timerbench.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import timerbench.com.timerbench.R;
import timerbench.com.timerbench.model.UserAlyarm;

/**
 * Created by Ivan on 14.05.2016.
 */
public class AlyarmAdapter extends BaseAdapter {

//    private final Context context;
    private final ArrayList<UserAlyarm> alarmArrayList;
    private final LayoutInflater inflater;

    public AlyarmAdapter(Context context, ArrayList<UserAlyarm> alarmArrayList) {
//        this.context = context;
        this.alarmArrayList = alarmArrayList;
//        inflater = LayoutInflater.from(this.context);
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return alarmArrayList.size();
    }

    @Override
    public UserAlyarm getItem(int position) {
        return alarmArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        View view = convertView;

        if (view == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        UserAlyarm userAlarm = alarmArrayList.get(position);
        mViewHolder.textViewMessage.setText(userAlarm.getMessage());
        mViewHolder.textViewTime.setText(userAlarm.getTime());

        return convertView;
    }

    private class MyViewHolder {
        TextView textViewMessage;
        TextView textViewTime;

        public MyViewHolder(View item) {
            textViewMessage = (TextView) item.findViewById(R.id.textViewMessage);
            textViewTime = (TextView) item.findViewById(R.id.textViewTime);
        }
    }
}
