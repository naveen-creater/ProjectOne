package com.example.projectone.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.projectone.R;
import com.example.projectone.databinding.ActivityDatabindingAnotherBinding;

public class DatabindingAnother extends AppCompatActivity {
    private ActivityDatabindingAnotherBinding anotherBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        anotherBinding = DataBindingUtil.setContentView(this,R.layout.activity_databinding_another);
    }
}