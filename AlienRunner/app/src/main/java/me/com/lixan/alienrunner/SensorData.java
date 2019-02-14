package me.com.lixan.alienrunner;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by LeakSun on 02/09/2019.
 */

public class SensorData implements SensorEventListener {

    private final String TAG = "SensorData";
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnometer;
    private boolean hasSensors;

    private float[] acceltData, magnoData;
    private float[] orientation = new float[3];
    private float[] startOrientation = null;


    public SensorData(){
        sensorManager = (SensorManager) MainActivity.context.getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager != null){
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }else{
            Log.e(TAG, "SensorData: SENSOR MANAGER IS NULL");
        }

    }

    public float[] getOrientation(){
        return orientation;
    }

    public float[] getStartOrientation(){
        return startOrientation;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            acceltData = sensorEvent.values;
        }
        else if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magnoData = sensorEvent.values;
        }

        if(acceltData != null && magnoData != null){
            float[] rotationMatrix = new float[9];
            float[] inclinationMatrix = new float[9];
            boolean isSuccess = SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, acceltData, magnoData);

            if(isSuccess){
                SensorManager.getOrientation(rotationMatrix, orientation);
                if(startOrientation == null){
                    startOrientation = new float[orientation.length];
                    System.arraycopy(orientation, 0, startOrientation, 0, orientation.length);
                    hasSensors = true;

                }
            }else{
                hasSensors = false;
                Log.e(TAG, "onSensorChanged: FAILED TO GET SENSORS ");
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public boolean hasSensors(){
        return hasSensors;
    }

    public void initGame(){
        startOrientation = null;
    }

    public void pauseSensors(){
        sensorManager.unregisterListener(this);
    }

    public void registerSensors(){
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);

    }

}
