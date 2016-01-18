package com.example.admin.pilotage;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mathieu on 13/01/2016.
 */
public class Inclinaison implements SensorEventListener {
    Context context;

    Thread tListenSensor;

    private  SensorManager mSensorManager;
    private  Sensor mAccelerometer;
    private  Sensor mMagnetic;
    //vecteur et variable des capteurs
    float[] accelerometerVector=new float[3];
    float[] inclinaison=new float[3];
    float[] magneticVector=new float[3];
    float[] resultMatrix=new float[9];
    float[] values=new float[3];
    float fy;
    float fz;


    public Inclinaison(Context rContext){
        this.context= rContext;
        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetic=mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(this, mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagnetic,SensorManager.SENSOR_DELAY_NORMAL);

        /*                mSensorManager = (SensorManager)getSystemService(MainActivity.SENSOR_SERVICE);
                mAccelerometer=mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mMagnetic=mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
       */

        accelerometerVector[0]=0;
        accelerometerVector[1]=0;
        accelerometerVector[2]=0;
        inclinaison[0]=0;
        inclinaison[1]=0;
        inclinaison[2]=0;

        //DemarreThread();
    }



    public float getRoll (){
        float fRoll=0;
        if( fy>= 45 ){
            fRoll=10;
        }else if(fy>=40){
            fRoll=8;
        }else if(fy>=35){
            fRoll=7;
        }else if(fy>=30){
            fRoll=6;
        }else if(fy>=25){
            fRoll=5;
        }else if(fy>=20){
            fRoll=4;
        }else if(fy>=15){
            fRoll=3;
        }else if(fy>=10){
            fRoll=2;
        }else if(fy>=5){
            fRoll=1;
        }else if(fy<=-45){
            fRoll=-10;
        }else if(fy<=-35){
            fRoll=-7;
        }else if(fy<=-30){
            fRoll=-6;
        }else if(fy<=-25){
            fRoll=-5;
        }else if(fy<=-20){
            fRoll=-4;
        }else if(fy<=-15){
            fRoll=-3;
        }else if(fy<=-10){
            fRoll=-2;
        }else if(fy<=-5){
            fRoll=-1;
        }
        if(fy > -5 && fy < 5){
            fRoll=0;
        }
        if(fy > 85 && fy < -85){
            fRoll=0;
        }
        return -fRoll;
    }

    public float getPitch (){
        float fPitch=0;
        if( fz>= 45 ){
            fPitch=-10;
        }else if(fz>=40){
            fPitch=-8;
        }else if(fz>=35){
            fPitch=-7;
        }else if(fz>=30){
            fPitch=-6;
        }else if(fz>=25){
            fPitch=-5;
        }else if(fz>=20){
            fPitch=-4;
        }else if(fz>=15){
            fPitch=-3;
        }else if(fz>=10){
            fPitch=-2;
        }else if(fz>=5){
            fPitch=-1;
        }else if(fz<=-45){
            fPitch=10;
        }else if(fz<=-35){
            fPitch=7;
        }else if(fz<=-30){
            fPitch=6;
        }else if(fz<=-25){
            fPitch=5;
        }else if(fz<=-20){
            fPitch=4;
        }else if(fz<=-15){
            fPitch=3;
        }else if(fz<=-10){
            fPitch=2;
        }else if(fz<=-5){
            fPitch=1;
        }
        if(fz > -5 && fz < 5){
            fPitch=0;
        }
        if(fy > 85 && fz < -85){
            fPitch=0;
        }

        return -fPitch;
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent se) {
        if (se.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerVector=se.values;

        }
        if(se.sensor.getType()== Sensor.TYPE_MAGNETIC_FIELD){
            magneticVector=se.values;
        }
        // Demander au sensorManager la matric de Rotation (resultMatric)
        SensorManager.getRotationMatrix(resultMatrix, null, accelerometerVector, magneticVector);
        // Demander au SensorManager le vecteur d'orientation associé (values)
        SensorManager.getOrientation(resultMatrix, values);
        // l'azimuth
        // le pitch
        fy = (float) Math.toDegrees(values[1]);
        // le roll
        fz = (float) Math.toDegrees(values[2]);
    }


    // display data
    /*
     *  create object able to receive information from sensor
     *  final : no derivation is possible (there is only 1 sensor)
     *  this syntax permits to declare an object of class SensorEventListener and at the same time instanciate it
     *  and declare the abstract method onSensorChanged
     */


    /*class TGetSensor implements Runnable {


        public void run() {


            SensorManager mSensorManager;
            Sensor mAccelerometer;
            Sensor mMagnetic;


            do {

                final SensorEventListener mSensorListener = new SensorEventListener() {
                    // action if on sensor changes
                    public void onSensorChanged(SensorEvent se)
                    {
                        if (se.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                            accelerometerVector=se.values;

                        }
                        if(se.sensor.getType()== Sensor.TYPE_MAGNETIC_FIELD){
                            magneticVector=se.values;
                        }
                        // Demander au sensorManager la matric de Rotation (resultMatric)
                        SensorManager.getRotationMatrix(resultMatrix, null, accelerometerVector, magneticVector);
                        // Demander au SensorManager le vecteur d'orientation associé (values)
                        SensorManager.getOrientation(resultMatrix, values);
                        // l'azimuth
                        // le pitch
                        fy = (float) Math.toDegrees(values[1]);
                        // le roll
                        fz = (float) Math.toDegrees(values[2]);
                    }

                    public void onAccuracyChanged(Sensor sensor, int accuracy) {} // not used
                };


            }while(true);

        }

    };

    public void DemarreThread(){

        // Appel du thread d'envoi du socket

        tListenSensor = new Thread(new TGetSensor());
        tListenSensor.start();
    }
*/
}
