package com.klocworx.androidproto;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;


public class LedControl extends AppCompatActivity {

    Button buttonOn;
    Button buttonOff;
    Button buttonDisonnect;
    SeekBar brightness;
    TextView brightnessIndicator;
    TextView sensorView1;

    String macAddress;
    private ProgressDialog progress;
    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothSocket bluetoothSocket = null;
    private boolean isBluetoothConnected = false;

    static final UUID bluetoothSppUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent parentIntent = getIntent();
        macAddress = parentIntent.getStringExtra(DeviceList.EXTRA_ADDRESS);

        setContentView(R.layout.activity_led_control);

        buttonOn = (Button)findViewById(R.id.buttonLedOn);
        buttonOff = (Button)findViewById(R.id.buttonLedOff);
        buttonDisonnect = (Button)findViewById(R.id.buttonDisconnect);
        brightness = (SeekBar)findViewById(R.id.seekBarBrightness);
        brightnessIndicator = (TextView)findViewById(R.id.textViewBrightness);
        sensorView1 = (TextView)findViewById(R.id.sensorView1);

        new ConnectBluetoothTask().execute();

        buttonOn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                turnOnLed();
            }
        });

        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffLed();
            }
        });

        buttonDisonnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect();
            }
        });

        brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){

                    int brightness = progress * 255 / 100;

                    brightnessIndicator.setText(String.valueOf(brightness));

                    try {
                        bluetoothSocket.getOutputStream().write(String.valueOf(brightness).getBytes());
                    } catch (IOException e) {
                        showMessage("Error");
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void Disconnect() {
        if(bluetoothSocket != null){
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                showMessage("Error");
            }
        }
        finish();   // return to first layout
    }

    private void getThresholdValue(){
        if(bluetoothSocket != null){
            try {
                bluetoothSocket.getOutputStream().write("GT".getBytes());
            } catch (IOException e) {
                showMessage("Error");
            }
        }
    }

    private void turnOffLed() {
        if(bluetoothSocket != null){
            try {
                bluetoothSocket.getOutputStream().write("TF".getBytes());
            } catch (IOException e) {
                showMessage("Error");
            }
        }
    }

    private void turnOnLed() {
        if(bluetoothSocket != null){
            try {
                bluetoothSocket.getOutputStream().write("TO".getBytes());
            } catch (IOException e) {
                showMessage("Error");
            }
        }
    }

    private void showMessage(String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private class ConnectBluetoothTask extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(LedControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (bluetoothSocket == null || !isBluetoothConnected)
                {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(macAddress);//connects to the device's macAddress and checks if it's available
                    bluetoothSocket = remoteDevice.createInsecureRfcommSocketToServiceRecord(bluetoothSppUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    bluetoothSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                showMessage("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                showMessage("Connected.");
                isBluetoothConnected = true;
            }
            progress.dismiss();
        }
    }
}
