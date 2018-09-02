package com.example.zubrein.final_thesis_poject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Zubrein on 3/27/2018.
 */

public class SensorService extends Service implements SensorEventListener {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    //DatePicker
    Calendar cal;
    SimpleDateFormat date;
    //Internet connection checker
    ConnectionDetector cd = new ConnectionDetector(SensorService.this);
    int id = 0;
    SensorManager sensorManager;
    Sensor accelerometer, gyroscope, proximity, light, barometer, gravity, temperature;
    String x, y, z, a, b, c;
    String mHour, mMin, mSec;
    String dt;

    int count1 = 1, count2 = 1;

    String x_value_accelerometer = "", y_value_accelerometer = "", z_value_accelerometer = "";
    String x_value_gyroscope = "", y_value_gyroscope = "", z_value_gyroscope = "";
    String value_light = "", value_proximity, value_barometer = "",value_temperature = "";
    String mNumber, internet_status;

    // int temp;
    ArrayList x_list_accelerometer = new ArrayList();
    ArrayList y_list_accelerometer = new ArrayList();
    ArrayList z_list_accelerometer = new ArrayList();
    ArrayList x_list_gyroscope = new ArrayList();
    ArrayList y_list_gyroscope = new ArrayList();
    ArrayList z_list_gyroscope = new ArrayList();
    ArrayList list_light = new ArrayList();


    double time_in_mili;
    long mil, milis;
    int second, minute, hour;
    String token;

    int temp;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        token = intent.getStringExtra("token");
        Toast.makeText(SensorService.this, token, Toast.LENGTH_SHORT).show();
        final SharedPreferences sharedPref = getSharedPreferences("reg_number", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        mNumber = sharedPref.getString("number", "");
        internet_status = sharedPref.getString("internet_status", "");

        Toast.makeText(SensorService.this, "Data recieving ....", Toast.LENGTH_SHORT).show();
        //Database instance

        //DatePicker
        cal = Calendar.getInstance();
        date = new SimpleDateFormat("dd-MM-yy");
        dt = date.format(cal.getTime());

        sensorInitializer();  //Sensor Initializing

        return START_STICKY;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(SensorService.this, "stopped", Toast.LENGTH_SHORT).show();
        //Accelerometer
        //accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.unregisterListener(SensorService.this, accelerometer);
        }
        if (gyroscope != null) {
            sensorManager.unregisterListener(SensorService.this, gyroscope);
        }
//        if (light != null) {
//            sensorManager.unregisterListener(SensorService.this, light);
//        }
//        if (temperature != null) {
//            sensorManager.unregisterListener(SensorService.this, temperature);
//        }
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        //Accelerometer
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            x = String.format(String.valueOf(event.values[0]));
            y = String.format(String.valueOf(event.values[1]));
            z = String.format(String.valueOf(event.values[2]));

            timehandler();

            if (second % 1 == 0 && time_in_mili < 500 ) {

                if (x_list_accelerometer.size() == 0) {
                    x_list_accelerometer.add(x);
                    y_list_accelerometer.add(y);
                    z_list_accelerometer.add(z);
                    x_value_accelerometer = x_list_accelerometer.get(0).toString();
                    y_value_accelerometer = y_list_accelerometer.get(0).toString();
                    z_value_accelerometer = z_list_accelerometer.get(0).toString();
                    temp = second;

                    secondHandler();
                }

                if (!x_value_accelerometer.equals("") && !y_value_accelerometer.equals("") && !y_value_accelerometer.equals("")) {
                    String time = mHour + "-" + mMin + "-" + mSec;
//                    String time = String.valueOf(count1);
//                    count1++;
                    SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE, null);
                    String insertQuery1="INSERT INTO "+"Accelerometer"
                            +" VALUES ('"+dt+"','"+time+"','"
                            +x_value_accelerometer+"','"
                            +y_value_accelerometer+"','"
                            +z_value_accelerometer+"','"
                            +token+"');";

                    db.execSQL(insertQuery1);
                    db.close();
                    x_value_accelerometer = "";
                    y_value_accelerometer = "";
                    z_value_accelerometer = "";
                }
            }
            if (time_in_mili> 700) {
                x_list_accelerometer = new ArrayList();
                y_list_accelerometer = new ArrayList();
                z_list_accelerometer = new ArrayList();
                x_value_accelerometer = "";
                y_value_accelerometer = "";
                z_value_accelerometer = "";

            }

        }


        //Gyroscope

        if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            a = String.format(String.valueOf(event.values[0]));
            b = String.format(String.valueOf(event.values[1]));
            c = String.format(String.valueOf(event.values[2]));

            timehandler();

            if (second % 1 == 0 && time_in_mili < 500) {

                if (x_list_gyroscope.size() == 0) {
                    x_list_gyroscope.add(a);
                    y_list_gyroscope.add(b);
                    z_list_gyroscope.add(c);
                    x_value_gyroscope = x_list_gyroscope.get(0).toString();
                    y_value_gyroscope = y_list_gyroscope.get(0).toString();
                    z_value_gyroscope = z_list_gyroscope.get(0).toString();
                    temp = second;

                    secondHandler();
                }

                if (!x_value_gyroscope.equals("") && !y_value_gyroscope.equals("") && !z_value_gyroscope.equals("")) {
                    String time = mHour + "-" + mMin + "-" + mSec;

                    SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE, null);
                    String insertQuery1="INSERT INTO "+"Gyroscope"+" VALUES ('"
                            +dt+"','"+time+"','"
                            +x_value_gyroscope+"','"
                            +y_value_gyroscope+"','"
                            +z_value_gyroscope+"','"
                            +token+"');";

                    db.execSQL(insertQuery1);
                    mil = System.currentTimeMillis();
                    db.close();

                    x_value_gyroscope = "";
                    y_value_gyroscope = "";
                    z_value_gyroscope = "";
                }
            }
            if (time_in_mili>700) {
                x_list_gyroscope = new ArrayList();
                y_list_gyroscope = new ArrayList();
                z_list_gyroscope = new ArrayList();
                x_value_gyroscope = "";
                y_value_gyroscope = "";
                z_value_gyroscope = "";
//                z_value_gyroscope = "";
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void sensorInitializer(){
        //Initializing sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Accelerometer
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelerometer != null){
            sensorManager.registerListener(SensorService.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        }
        //Gyroscope
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(gyroscope != null){
            sensorManager.registerListener(SensorService.this,gyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void timehandler(){
        mil = System.currentTimeMillis();
        milis = mil;
        time_in_mili = milis % 1000;
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
    public void secondHandler(){
        mSec = String.valueOf(temp);
        if(mSec.equals("0")) {mSec = "00";}if(mSec.equals("1")) {mSec = "01";}if(mSec.equals("2")) {mSec = "02";}
        if(mSec.equals("3")) {mSec = "03";}if(mSec.equals("4")) {mSec = "04";}if(mSec.equals("5")) {mSec = "05";}
        if(mSec.equals("6")) {mSec = "06";}if(mSec.equals("7")) {mSec = "07";}if(mSec.equals("8")) {mSec = "08";}
        if(mSec.equals("9")) {mSec = "09";}
    }




}
