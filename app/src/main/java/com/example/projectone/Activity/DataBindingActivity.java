package com.example.projectone.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.projectone.R;
import com.example.projectone.databinding.ActivityDataBindingBinding;
import com.example.projectone.databinding.ActivityDatabindingAnotherBinding;

public class DataBindingActivity extends AppCompatActivity {
    private ActivityDataBindingBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_data_binding);
    }
}