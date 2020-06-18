package com.example.projectone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.projectone.R;
import com.example.projectone.Service.ToyVpnService;

public class VpnActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mServerAddress;
    private TextView mServerPort;
    private TextView mSharedSecret;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpn);

        mServerAddress = (TextView) findViewById(R.id.address);
        mServerPort = (TextView) findViewById(R.id.port);
        mSharedSecret = (TextView) findViewById(R.id.secret);

        findViewById(R.id.connect).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = VpnService.prepare(this);
        if (intent != null) {
            startActivityForResult(intent, 0);
        } else {
            onActivityResult(0, RESULT_OK, null);
        }
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        if (result == RESULT_OK) {
            String prefix = getPackageName();
            Intent intent = new Intent(this, ToyVpnService.class)
                    .putExtra(prefix + ".ADDRESS", mServerAddress.getText().toString())
                    .putExtra(prefix + ".PORT", mServerPort.getText().toString())
                    .putExtra(prefix + ".SECRET", mSharedSecret.getText().toString());
            startService(intent);
        }
    }
}
