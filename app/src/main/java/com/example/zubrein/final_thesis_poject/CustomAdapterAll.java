package com.example.zubrein.final_thesis_poject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterAll extends ArrayAdapter<String> {

    ArrayList<String> date ;
    ArrayList<String> time ;
    ArrayList<String> Acc_X ;
    ArrayList<String> Acc_Y ;
    ArrayList<String> Acc_Z ;
    ArrayList<String> Gyro_X ;
    ArrayList<String> Gyro_Y ;
    ArrayList<String> Gyro_Z ;
    ArrayList<String> light_X ;
    ArrayList<String> token ;
    Context ctx;

    public CustomAdapterAll(Context context, ArrayList<String> date,ArrayList<String> time,
                            ArrayList<String> Acc_X,ArrayList<String> Acc_Y,ArrayList<String> Acc_Z,
                            ArrayList<String> Gyro_X,ArrayList<String> Gyro_Y,ArrayList<String> Gyro_Z
            ,ArrayList<String> token) {
        super(context, R.layout.model_three_value);
        this.date = date;
        this.time = time;
        this.Acc_X = Acc_X;
        this.Acc_Y = Acc_Y;
        this.Acc_Z = Acc_Z;
        this.Gyro_X = Gyro_X;
        this.Gyro_Y = Gyro_Y;
        this.Gyro_Z = Gyro_Z;
        this.light_X = light_X;
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

            convertView = inflate.inflate(R.layout.model_all, parent, false);

            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.Acc_X = (TextView) convertView.findViewById(R.id.acc_x);
            holder.Acc_Y = (TextView) convertView.findViewById(R.id.acc_y);
            holder.Acc_Z = (TextView) convertView.findViewById(R.id.acc_z);
            holder.Gyro_X = (TextView) convertView.findViewById(R.id.gyro_x);
            holder.Gyro_Y = (TextView) convertView.findViewById(R.id.gyro_y);
            holder.Gyro_Z = (TextView) convertView.findViewById(R.id.gyro_z);
            holder.light_X = (TextView) convertView.findViewById(R.id.light);
            holder.token = (TextView) convertView.findViewById(R.id.token);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.date.setText(date.get(position));
        holder.time.setText(time.get(position));
        holder.Acc_X.setText(Acc_X.get(position));
        holder.Acc_Y.setText(Acc_Y.get(position));
        holder.Acc_Z.setText(Acc_Z.get(position));
        holder.Gyro_X.setText(Gyro_X.get(position));
        holder.Gyro_Y.setText(Gyro_Y.get(position));
        holder.Gyro_Z.setText(Gyro_Z.get(position));
       // holder.light_X.setText(light_X.get(position));
        holder.token.setText(token.get(position));


        return convertView;
    }

    static class ViewHolder {
        TextView date;
        TextView time;
        TextView Acc_X ;
        TextView Acc_Y ;
        TextView Acc_Z ;
        TextView Gyro_X ;
        TextView Gyro_Y ;
        TextView Gyro_Z ;
        TextView light_X ;
        TextView token ;
    }
}
