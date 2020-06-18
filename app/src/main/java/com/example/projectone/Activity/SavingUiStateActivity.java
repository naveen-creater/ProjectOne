package com.example.projectone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectone.R;

public class SavingUiStateActivity extends AppCompatActivity {
    Button button;
    TextView textView;
    int counter;
    boolean onOrOff;
    Switch aSwitch;
    String phoneNo;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_ui_state);

        initView();
        onOrOff = aSwitch.isChecked();


            if (savedInstanceState != null) {
                String message = savedInstanceState.getString("message");
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();

                counter = savedInstanceState.getInt("counter", 0);
                phoneNo = savedInstanceState.getString("phoneNo","5555");
            }


        if(phoneNo!=null)
        {
            phone.setText(phoneNo);
        }
        textView.setText(String.valueOf(counter));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = Integer.valueOf(textView.getText().toString()) + 1;
                textView.setText(String.valueOf(counter));
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onOrOff = isChecked;
            }
        });
    }

    private void initView() {
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        aSwitch = findViewById(R.id.switch1);
        phone = findViewById(R.id.phone);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        phoneNo = phone.getText().toString();
        if (onOrOff) {
            outState.putString("message", "This is a saved message");
            outState.putInt("counter", counter);
            outState.putString("phoneNo",phoneNo);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (onOrOff) {
            Toast.makeText(getApplicationContext(), "onRestoreInstanceState", Toast.LENGTH_SHORT).show();
            counter = savedInstanceState.getInt("counter", 0);
            phoneNo = savedInstanceState.getString("phoneNo","5555");
        }


    }

}