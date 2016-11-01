package com.klocworx.androidproto;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DeviceList extends AppCompatActivity {

    // widgets
    Button btnPaired;
    ListView deviceList;

    // bluetooth stuff
    private BluetoothAdapter bluetoothAdapter = null;
    public static String EXTRA_ADDRESS = "device_address";
    private static int MAC_ADDRESS_LENGTH = 17;     // this length includes the colons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        // create widgets
        btnPaired = (Button)findViewById(R.id.buttonDeviceList);
        deviceList = (ListView)findViewById(R.id.listViewDevices);

        if(!doesDeviceHaveBlueTooth()){
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();

            finish();
        }

        if(!bluetoothAdapter.isEnabled()){
            askUserToTurnBluetoothOn();
        }

        btnPaired.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getPairedDevices();
            }
        });
    }

    private void getPairedDevices() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        List<String> deviceItems = new ArrayList<>();

        if(pairedDevices.size() > 0){
            for(BluetoothDevice device : pairedDevices){
                deviceItems.add(device.getName() + "\n" + device.getAddress());
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "No paired bluetooth devices found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceItems);
        deviceList.setAdapter(adapter);
        deviceList.setOnItemClickListener(deviceClickListener);
    }

    private AdapterView.OnItemClickListener deviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String info = ((TextView) view).getText().toString();
            String macAddress = info.substring(info.length() - MAC_ADDRESS_LENGTH);

            launchLedControlActivity(macAddress);
        }
    };

    private void launchLedControlActivity(String macAddress) {

        Intent ledControlActivity = new Intent(DeviceList.this, LedControl.class);
        ledControlActivity.putExtra(EXTRA_ADDRESS, macAddress);

        startActivity(ledControlActivity);
    }

    private void askUserToTurnBluetoothOn() {
        Intent tunrBluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(tunrBluetoothOn, 1);
    }

    private boolean doesDeviceHaveBlueTooth(){
        return (this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()) != null;
    }
}
