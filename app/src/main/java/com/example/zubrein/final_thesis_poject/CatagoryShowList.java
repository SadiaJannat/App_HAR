package com.example.zubrein.final_thesis_poject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class CatagoryShowList extends AppCompatActivity {

    ListView lv ;
    ArrayList<String> list_date ;
    ArrayList<String> list_time ;
    ArrayList<String> list_Acc_X ;
    ArrayList<String> list_Acc_Y ;
    ArrayList<String> list_Acc_Z ;
    ArrayList<String> list_Gyro_X ;
    ArrayList<String> list_Gyro_Y ;
    ArrayList<String> list_Gyro_Z ;
    ArrayList<String> list_light_X ;
    ArrayList<String> list_token ;
    TextView name;
    CustomAdapterAll adapter;
    Button save;

    long mil, milis;
    int second, minute, hour;
    String mHour, mMin, mSec;
    String dt;
    //DatePicker
    Calendar cal;
    SimpleDateFormat datte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory_show_list);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        cal = Calendar.getInstance();
        datte = new SimpleDateFormat("dd-MM");
        dt = datte.format(cal.getTime());

        Intent intent = getIntent();
        final String mName = intent.getStringExtra("token1").toUpperCase();
        final String token = intent.getStringExtra("token2");
        list_date = new ArrayList<>();
        list_time = new ArrayList<>();
        list_Acc_X = new ArrayList<>();
        list_Acc_Y = new ArrayList<>();
        list_Acc_Z = new ArrayList<>();
        list_Gyro_X = new ArrayList<>();
        list_Gyro_Y = new ArrayList<>();
        list_Gyro_Z = new ArrayList<>();
        list_light_X = new ArrayList<>();
        list_token = new ArrayList<>();


        lv = findViewById(R.id.lv);
        name = findViewById(R.id.name);
        save = findViewById(R.id.floating);

        name.setText(mName);
        final String database = "SELECT DISTINCT " +
                "Accelerometer.date,Accelerometer.time,Accelerometer.Accelerometer_X,Accelerometer.Accelerometer_Y,Accelerometer.Accelerometer_Z," +
                "Gyroscope.Gyroscope_X,Gyroscope.Gyroscope_Y,Gyroscope.Gyroscope_Z" +
                " FROM Accelerometer JOIN Gyroscope  ON Accelerometer.time = Gyroscope.time   WHERE Accelerometer.TOKEN="+token+" AND Gyroscope.TOKEN="+token;

        final SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE, null);


        //// Database Query

        Cursor cursor=db.rawQuery(database, null);

        int rowCount=cursor.getCount();

        if(rowCount<=0)
        {
            Toast.makeText(getApplicationContext(), "No data available", Toast.LENGTH_SHORT).show();
        }
        else
        {
            cursor.moveToFirst();
            String allData="";

            do {
                String date=cursor.getString(cursor.getColumnIndex("date"));
                String time=cursor.getString(cursor.getColumnIndex("time"));
                String Accelerometer_X=cursor.getString(cursor.getColumnIndex("Accelerometer_X"));
                String Accelerometer_Y=cursor.getString(cursor.getColumnIndex("Accelerometer_Y"));
                String Accelerometer_Z=cursor.getString(cursor.getColumnIndex("Accelerometer_Z"));
                String Gyroscope_X=cursor.getString(cursor.getColumnIndex("Gyroscope_X"));
                String Gyroscope_Y=cursor.getString(cursor.getColumnIndex("Gyroscope_Y"));
                String Gyroscope_Z=cursor.getString(cursor.getColumnIndex("Gyroscope_Z"));
                //String Light_X=cursor.getString(cursor.getColumnIndex("Light_X"));

                list_date.add(date);
                list_time.add(time);
                list_Acc_X.add(Accelerometer_X);
                list_Acc_Y.add(Accelerometer_Y);
                list_Acc_Z.add(Accelerometer_Z);
                list_Gyro_X.add(Gyroscope_X);
                list_Gyro_Y.add(Gyroscope_Y);
                list_Gyro_Z.add(Gyroscope_Z);
                //list_light_X.add(Light_X);
                list_token.add(token);



            } while (cursor.moveToNext());
        }

        ///---END-----

        adapter = new CustomAdapterAll(CatagoryShowList.this,list_date,list_time,list_Acc_X,list_Acc_Y,list_Acc_Z,list_Gyro_X,list_Gyro_Y,list_Gyro_Z,list_token);
        lv.setAdapter(adapter);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=db.rawQuery(database, null);
                String allData="";

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
                        String Accelerometer_X=cursor.getString(cursor.getColumnIndex("Accelerometer_X"));
                        String Accelerometer_Y=cursor.getString(cursor.getColumnIndex("Accelerometer_Y"));
                        String Accelerometer_Z=cursor.getString(cursor.getColumnIndex("Accelerometer_Z"));
                        String Gyroscope_X=cursor.getString(cursor.getColumnIndex("Gyroscope_X"));
                        String Gyroscope_Y=cursor.getString(cursor.getColumnIndex("Gyroscope_Y"));
                        String Gyroscope_Z=cursor.getString(cursor.getColumnIndex("Gyroscope_Z"));

                        allData=allData+time+","+Accelerometer_X+","+Accelerometer_Y+","+Accelerometer_Z+","
                                +Gyroscope_X+","+Gyroscope_Y+","+Gyroscope_Z+","
                                +token+"\n";


                    } while (cursor.moveToNext());
                }
                saveTextAsFile(mName,allData);
//                db.execSQL("delete from "+ mName);
                db.close();
            }
        });

    }

    public void saveTextAsFile(String filename,String content){

        timehandler();

        String time = mHour + "-" + mMin + "-" + mSec;

        String fileName = filename+" ("+dt+")("+time+").txt";

        String data = "time,"+"Accelerometer_X,"+"Accelerometer_Y,"+"Accelerometer_Z,"
                +"Gyroscope_X,"+"Gyroscope_Y,"+"Gyroscope_Z,"+"Token"+"\n" + content;

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
            Toast.makeText(CatagoryShowList.this,"Saved",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(CatagoryShowList.this,"File not saved",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(CatagoryShowList.this,"Error saving",Toast.LENGTH_SHORT).show();
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
