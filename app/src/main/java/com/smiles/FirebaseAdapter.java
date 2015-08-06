package com.smiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by ffas on 6/9/15.
 */
public class FirebaseAdapter extends BaseAdapter {

    LinkedList<SingleRow> list;
    Context context;
    LayoutInflater inflater;

    public FirebaseAdapter(LinkedList<SingleRow> list, Context c) {
        this.list = list;
        this.context = c;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row, parent, false);
            viewHolder.t1 = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.t2 = (TextView) convertView.findViewById(R.id.textView2);
            viewHolder.t3 = (TextView) convertView.findViewById(R.id.textView3);
            viewHolder.i1 = (ImageView) convertView.findViewById(R.id.read_icon);
            convertView.setTag(viewHolder);
        } else  {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SingleRow temp = list.get(position);

        viewHolder.t1.setText(temp.getMessage());
        viewHolder.t2.setText(temp.getDetail());
        if( !temp.getDate().isEmpty() ) {
            viewHolder.t3.setText(temp.getDate());
            viewHolder.t3.setVisibility(View.VISIBLE);
        }

        if (temp.getFlag()) {
            viewHolder.i1.setVisibility(View.VISIBLE);
        }
        else
            viewHolder.i1.setVisibility(View.INVISIBLE);


        return convertView;
    }

    public class ViewHolder {
        TextView t1;
        TextView t2;
        TextView t3;
        ImageView i1;

    }

}
