package com.example.zubrein.final_thesis_poject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Show_list_one extends AppCompatActivity {

    ListView lv ;
    ArrayList<String> list_date ;
    ArrayList<String> list_time ;
    ArrayList<String> list_xx ;
    ArrayList<String> list_token ;
    TextView name;
    CustomAdapterOne adapter;
    FloatingActionButton floatingActionButton;

    long mil, milis;
    int second, minute, hour;
    String mHour, mMin, mSec;
    String dt;
    //DatePicker
    Calendar cal;
    String allData;
    SimpleDateFormat datte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);


        cal = Calendar.getInstance();
        datte = new SimpleDateFormat("dd-MM-yy");
        dt = datte.format(cal.getTime());


        Intent intent = getIntent();
        final String mName = intent.getStringExtra("name");
        list_date = new ArrayList<>();
        list_time = new ArrayList<>();
        list_xx = new ArrayList<>();
        list_token = new ArrayList<>();

        lv = findViewById(R.id.lv);
        name = findViewById(R.id.name);
        floatingActionButton = findViewById(R.id.floating);

        name.setText(mName);

        final SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE, null);
        Cursor cursor=db.rawQuery("SELECT * FROM "+mName+";", null);

        int rowCount=cursor.getCount();

        if(rowCount<=0)
        {
            Toast.makeText(getApplicationContext(), "No data available", Toast.LENGTH_SHORT).show();
        }
        else
        {
            cursor.moveToFirst();

            do {
                String date=cursor.getString(cursor.getColumnIndex("date"));
                String time=cursor.getString(cursor.getColumnIndex("time"));
                String x=cursor.getString(cursor.getColumnIndex(mName+"_X"));
                String token=cursor.getString(cursor.getColumnIndex("TOKEN"));
                //allData=allData+" "+date+" "+time+"\n";

                list_date.add(date);
                list_time.add(time);
                list_xx.add(x);
                list_token.add(token);

            } while (cursor.moveToNext());

            //Toast.makeText(getApplicationContext(), allData, Toast.LENGTH_LONG).show();
        }

        adapter = new CustomAdapterOne(Show_list_one.this,list_date,list_time,list_xx,list_token);
        lv.setAdapter(adapter);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=db.rawQuery("SELECT * FROM "+mName+";", null);

                int rowCount=cursor.getCount();

                if(rowCount<=0)
                {
                    Toast.makeText(getApplicationContext(), "No data available", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    cursor.moveToFirst();
                    allData="";

                    do {
                        String date=cursor.getString(cursor.getColumnIndex("date"));
                        String time=cursor.getString(cursor.getColumnIndex("time"));
                        String x=cursor.getString(cursor.getColumnIndex(mName+"_X"));
                        String token=cursor.getString(cursor.getColumnIndex("TOKEN"));
                        allData=allData+" "+date+" "+time+" "+x+" "+token+" "+"\n";
                        list_date.add(date);
                        list_time.add(time);
                        list_xx.add(x);
                        list_token.add(token);

                    } while (cursor.moveToNext());
                }
                saveTextAsFile(mName,allData);
                db.close();
            }
        });

    }

    public void saveTextAsFile(String filename,String content){

        timehandler();

        String time = mHour + "-" + mMin + "-" + mSec;

        String fileName = filename+" ("+dt+").txt";

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(Show_list_one.this,"Saved",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(Show_list_one.this,"File not saved",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(Show_list_one.this,"Error saving",Toast.LENGTH_SHORT).show();
        }


    }

    public void timehandler(){
        mil = System.currentTimeMillis();
        milis = mil;
        second = (int) (milis / 1000);
        minute = second / 60;
        hour = minute / 60;
        milis = milis % 1000;
        second = second % 60;
        minute = minute % 60;
        hour = (hour % 24) + 6;
        if (hour >= 24) {
            hour = hour % 24;
        }
        mHour = String.valueOf(hour);
        mMin = String.valueOf(minute);
        mSec = String.valueOf(second);
        if(mHour.equals("0")) {mHour = "00";}if(mHour.equals("1")) {mHour = "01";}if(mHour.equals("2")) {mHour = "02";}
        if(mHour.equals("3")) {mHour = "03";}if(mHour.equals("4")) {mHour = "04";}if(mHour.equals("5")) {mHour = "05";}
        if(mHour.equals("6")) {mHour = "06";}if(mHour.equals("7")) {mHour = "07";}if(mHour.equals("8")) {mHour = "08";}
        if(mHour.equals("9")) {mHour = "09";}
        if(mMin.equals("0")) {mMin = "00";}if(mMin.equals("1")) {mMin = "01";}if(mMin.equals("2")) {mMin = "02";}
        if(mMin.equals("3")) {mMin = "03";}if(mMin.equals("4")) {mMin = "04";}if(mMin.equals("5")) {mMin = "05";}
        if(mMin.equals("6")) {mMin = "06";}if(mMin.equals("7")) {mMin = "07";}if(mMin.equals("8")) {mMin = "08";}
        if(mMin.equals("9")) {mMin = "09";}
    }

}
