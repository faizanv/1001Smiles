package com.smiles;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by ffas on 6/22/15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<SingleRow> data = Collections.emptyList();
    private Context context;
    ClickListener clickListener;

    public RecyclerAdapter(Context context, List<SingleRow> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }
    
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SingleRow temp = data.get(position);
        holder.t1.setText(temp.getMessage());
//        holder.t2.setText(temp.getDetail());
        holder.t3.setText(temp.getDate());

        if (temp.getFlag()) {
            holder.i1.setVisibility(View.VISIBLE);
        } else {
            holder.i1.setVisibility(View.INVISIBLE);
        }

        switch (temp.getType().toLowerCase()) {
            case "marketing":
                holder.background.setBackgroundColor(Color.parseColor("#FF6F59"));
                break;
            case "alert":
                holder.background.setBackgroundColor(Color.parseColor("#f02244"));
                break;
            case "content":
                holder.background.setBackgroundColor(Color.parseColor("#0BACD3"));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(ClickListener l) {
        this.clickListener = l;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t1;
//        TextView t2;
        TextView t3;
        ImageView i1;
        RelativeLayout background;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            t1 = (TextView) itemView.findViewById(R.id.textView);
//            t2 = (TextView) itemView.findViewById(R.id.textView2);
            t3 = (TextView) itemView.findViewById(R.id.textView3);
            i1 = (ImageView) itemView.findViewById(R.id.read_icon);
            background = (RelativeLayout) itemView.findViewById(R.id.card_layout);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getLayoutPosition());
            }
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }
}
