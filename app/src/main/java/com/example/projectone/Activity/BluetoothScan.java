package com.example.projectone.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projectone.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothScan extends AppCompatActivity {
    private Button turnOn;
    private Button turnOff;
    private Button getVisibleDevice;
    private Button getPairedDevice;
    private ImageView getdiscoverDevice;

    BluetoothAdapter bluetoothAdapter;
    ListView listView;
    private Set<BluetoothDevice> pairedDevices;
    public static final int REQUEST_ENABLE_BT = 1;
    private ArrayAdapter    <String> mBTArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_scan);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Toast.makeText(BluetoothScan.this,"This device is not suppoted for BlueTooth",Toast.LENGTH_LONG).show();
        }
        //initview
        initView();

        //listeners
        listeners();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_OK)
        {
            if(requestCode == REQUEST_ENABLE_BT){
                Toast.makeText(this, "Turn On Bluetooth", Toast.LENGTH_SHORT).show();
            }
        }else {
//            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }



    }

    private void listeners() {
        turnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }else {
                    Toast.makeText(BluetoothScan.this, "Already On BlueTooth", Toast.LENGTH_SHORT).show();
                }
            }
        });

        turnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothAdapter.disable();
                Toast.makeText(getApplicationContext(), "Turned off" ,Toast.LENGTH_LONG).show();
            }
        });

        getPairedDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pairedDevices = bluetoothAdapter.getBondedDevices();
                ArrayList list = new ArrayList();
                if(!pairedDevices.isEmpty()){
                    for(BluetoothDevice bt : pairedDevices)
                    {
                        list.add(bt.getName());
                    }

                    Toast.makeText(getApplicationContext(), "Showing Paired Devices",Toast.LENGTH_SHORT).show();

                    final ArrayAdapter adapter = new ArrayAdapter(BluetoothScan.this,android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(adapter);
                }
                else {
                    Toast.makeText(BluetoothScan.this, "Paired Devices list is Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        getVisibleDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);// 120 sconds not visible Bluetooth
                startActivity(discoverableIntent);

            }
        });

        getdiscoverDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discover();
            }
        });
    }

    private void initView() {
        turnOn = findViewById(R.id.turn_on);
        turnOff = findViewById(R.id.turn_off);
        getVisibleDevice = findViewById(R.id.getvisible);
        getPairedDevice = findViewById(R.id.list_of_device);
        listView = findViewById(R.id.listview);
        getdiscoverDevice = findViewById(R.id.imageView2);
        mBTArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
    }

    private void discover(){
        // Check if the device is already discovering
        if(bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
            Toast.makeText(getApplicationContext(),"Discovery stopped",Toast.LENGTH_SHORT).show();
        }
        else{
            if(bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.startDiscovery();
                Toast.makeText(getApplicationContext(), "Discovery started", Toast.LENGTH_SHORT).show();
                registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            }
            else{
                Toast.makeText(getApplicationContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
            }
        }
    }
    final BroadcastReceiver blReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // add the name to the list
                if (device != null) {
                    mBTArrayAdapter.add(device.getName()+"\n"+device.getAddress());
                }
                System.out.println(mBTArrayAdapter+ "List of device");
//                final ArrayAdapter adapter = new ArrayAdapter(BluetoothScan.this,android.R.layout.simple_list_item_1, mBTArrayAdapter);
                listView.setAdapter(mBTArrayAdapter);
            }
        }
    };


}
