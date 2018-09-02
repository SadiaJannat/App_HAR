package com.example.zubrein.final_thesis_poject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {

    ArrayList<String> date ;
    ArrayList<String> time ;
    ArrayList<String> xx ;
    ArrayList<String> yy ;
    ArrayList<String> zz ;
    ArrayList<String> token ;
    Context ctx;

    public CustomAdapter(Context context, ArrayList<String> date,ArrayList<String> time,ArrayList<String> xx,ArrayList<String> yy,ArrayList<String> zz,ArrayList<String> token) {
        super(context, R.layout.model_three_value);
        this.date = date;
        this.time = time;
        this.xx = xx;
        this.yy = yy;
        this.zz = zz;
        this.token = token;
        this.ctx = context;
    }

    @Override
    public int getCount() {
        return date.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflate = (LayoutInflater)
                    ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflate.inflate(R.layout.model_three_value, parent, false);

            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.x = (TextView) convertView.findViewById(R.id.X);
            holder.y = (TextView) convertView.findViewById(R.id.Y);
            holder.z = (TextView) convertView.findViewById(R.id.Z);
            holder.tokenn = (TextView) convertView.findViewById(R.id.token);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.date.setText(date.get(position));
        holder.time.setText(time.get(position));
        holder.x.setText(xx.get(position));
        holder.y.setText(yy.get(position));
        holder.z.setText(zz.get(position));
        holder.tokenn.setText(token.get(position));

        return convertView;
    }

    static class ViewHolder {
        TextView date;
        TextView time;
        TextView x;
        TextView y;
        TextView z;
        TextView tokenn;
    }
}
