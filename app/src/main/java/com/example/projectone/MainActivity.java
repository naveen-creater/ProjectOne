package com.example.projectone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectone.Activity.BluetoothScan;
import com.example.projectone.Activity.GestureDetection;
import com.example.projectone.Activity.PackageManagerActivity;
import com.example.projectone.Activity.SavingUiStateActivity;
import com.example.projectone.Activity.SearchviewActivity;
import com.example.projectone.Activity.ViewModelSample;
import com.example.projectone.Activity.VpnActivity;
import com.example.projectone.Activity.WifiScanning;
import com.example.projectone.Model.ListDataRecycler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<ListDataRecycler> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initView
        initView();
        recyclerView.setAdapter(new MyAdapterRecycle(datas,MainActivity.this));

    }

    private void initView() {
        datas.add(new ListDataRecycler("BlueTooth",R.drawable.bluetooths,"Bluetooth is a wireless technology standard used for exchanging data between fixed and mobile devices over short distances", BluetoothScan.class));
        datas.add(new ListDataRecycler("VPN",R.drawable.vpnlogo,"A virtual private network, or VPN, is an encrypted connection over the Internet from a device to a network", VpnActivity.class));
        datas.add(new ListDataRecycler("WIFI",R.drawable.wifilogo,"Advertisements. WiFi is a universal wireless networking technology that utilizes radio frequencies to transfer data.", WifiScanning.class));
        datas.add(new ListDataRecycler("GestureDetection",R.drawable.gesturelogo,"A gesture is a form of non-verbal communication or non-vocal communication in which visible bodily actions communicate particular messages", GestureDetection.class));
        datas.add(new ListDataRecycler("SavingUIState",R.drawable.savinguilogo,"Preserving and restoring an activity’s UI state in a timely fashion across system-initiated activity or application destruction", SavingUiStateActivity.class));
        datas.add(new ListDataRecycler("ViewModel",R.drawable.viewmodellogo,"he ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way", ViewModelSample.class));
        datas.add(new ListDataRecycler("SearchView",R.drawable.searchlogo,"try to find something by looking or otherwise seeking carefully and thoroughly.", SearchviewActivity.class));
        datas.add(new ListDataRecycler("PakageManager",R.drawable.pakagemanagerlogo,"Class for retrieving various kinds of information related to the application packages that are currently installed on the device", PackageManagerActivity.class));

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.border));
        recyclerView.addItemDecoration(divider);

        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.optimization:
                setBatteryOptimizer();
                return true;
            case R.id.settings:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {

            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            boolean isIgnoringBatteryOptimizations = false;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                isIgnoringBatteryOptimizations = pm.isIgnoringBatteryOptimizations(getPackageName());
            }
            if (isIgnoringBatteryOptimizations) {
                // Ignoring battery optimization
            } else {
                // Not ignoring battery optimization

                //setBatteryOptimizer();
            }
        }
    }

    private void setBatteryOptimizer() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            //check the app is IgnoringBatteryOptimizations
            if (pm != null && !pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
//                startActivity(intent);
                startActivityForResult(intent, 200);
            }
        }
    }

    public class MyAdapterRecycle extends RecyclerView.Adapter<MyAdapterRecycle.MyViewHolder>{
        private List<ListDataRecycler> dataRecyclers;
        private Context context;
        public MyAdapterRecycle(List<ListDataRecycler> data, Context context){
            this.dataRecyclers = data;
            this.context = context;

        }


        @NonNull
        @Override
        public MyAdapterRecycle.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.recycler_list_main, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapterRecycle.MyViewHolder holder, int position) {
            final ListDataRecycler data = dataRecyclers.get(position);

            holder.imageView.setImageResource(data.getImgId());
            holder.title.setText(data.getTitle());
            holder.description.setText(data.getDescription());

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(context,data.getObject()));
                }
            });


        }

        @Override
        public int getItemCount() {
            return dataRecyclers.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView title;
            private TextView description;
            LinearLayout linearLayout;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                this.imageView = itemView.findViewById(R.id.image_list);
                this.title = itemView.findViewById(R.id.re_title);
                this.description = itemView.findViewById(R.id.re_descrption);
                this.linearLayout = itemView.findViewById(R.id.linearlay);
            }
        }
    }

}
