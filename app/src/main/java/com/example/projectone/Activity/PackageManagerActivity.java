package com.example.projectone.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectone.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageManagerActivity extends AppCompatActivity {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 208;
    public static final int PERMISSIONS_REQUEST_LOCATION = 209;
    public static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 201;
    public static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 203;

    private Button location;
    private Button phoneState;
    private Button multiple;
    private Button pakageInfo;
    private Button getKeyHash;
    private ListView listView;
    private TextView keyHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_manager);
        init();
        listeners();

    }

    private void listeners() {
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationPermission();
            }
        });
        phoneState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPhoneStatePermission();
            }
        });
        multiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMultiplePermission();
            }
        });

        pakageInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ApplicationInfo> apps = loadAppList(PackageManagerActivity.this);
                System.out.println(apps.get(0) +": list of applications ");

                System.out.println(getVersionName(PackageManagerActivity.this)+": Appversion name");

                final ArrayAdapter adapter = new ArrayAdapter(PackageManagerActivity.this,android.R.layout.simple_list_item_1, apps);
                listView.setAdapter(adapter);

            }
        });

        getKeyHash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(getKeyHash(PackageManagerActivity.this)+": Hashkey for this pakage");


                PackageInfo info;
                try {
                    String packageName = PackageManagerActivity.this.getPackageName();
                    info = getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
                    for (Signature signature : info.signatures) {
                        MessageDigest md;
                        md = MessageDigest.getInstance("SHA");
                        md.update(signature.toByteArray());
                        String something = new String(Base64.encode(md.digest(), 0));
                        //String something = new String(Base64.encodeBytes(md.digest()));
                        Log.e("hash key", something);
                        keyHash.setText(something);
                    }
                } catch (PackageManager.NameNotFoundException e1) {
                    Log.e("name not found", e1.toString());
                } catch (NoSuchAlgorithmException e) {
                    Log.e("no such an algorithm", e.toString());
                } catch (Exception e) {
                    Log.e("exception", e.toString());
                }
            }
        });
    }

    private void init() {
        location = findViewById(R.id.location_permision);
        phoneState = findViewById(R.id.phonestate);
        multiple = findViewById(R.id.multiple);
        pakageInfo = findViewById(R.id.pakageinfo);
        getKeyHash = findViewById(R.id.keyhash);
        listView = findViewById(R.id.listofdevicez);
        keyHash = findViewById(R.id.key_hash);

    }

    //mitra
    public void checkPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
        } else {
            checkMultiplePermission();

        }
    }
}
    public void checkLocationPermission() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION);

        }
    }


}
    public void checkMultiplePermission() {

    int camera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
    int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

    int audio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
    int sendSms = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

    List<String> listPermissionsNeeded = new ArrayList<>();

    if (camera != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
    }
    if (storage != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    if (sendSms != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(android.Manifest.permission.SEND_SMS);
    }

    if (audio != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(android.Manifest.permission.RECORD_AUDIO);
    }

    if (!listPermissionsNeeded.isEmpty()) {
        ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);

    }


}
    public void checkPhoneStatePermission() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSIONS_REQUEST_READ_PHONE_STATE);

        } else {

            checkPermission();


        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {


                AlertDialog.Builder builder = new AlertDialog.Builder(PackageManagerActivity.this);
                builder.setCancelable(false);
                builder.setTitle(getResources().getString(R.string.app_name)).setMessage("The message is Request and check permission");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        checkPermission();
                    }
                });

                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                        checkMultiplePermission();
                    }
                });


                AlertDialog alertDialog = builder.create();
                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {


                    }
                });

                alertDialog.show();


            } else {
                checkMultiplePermission();
            }


        }


    }



    public static String getAppSource(Context context, String metaName) {
        String result = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                result = appInfo.metaData.getString(metaName);
                System.out.println(result+": applications source ");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
//            JLog.e(e.toString());
            Log.e("getAppsource",e.toString());
        }
        return result;
    }
    public static List<ApplicationInfo> loadAppList(Context context) {
        List<ApplicationInfo> applicationInfoList = context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        Collections.sort(applicationInfoList, new ApplicationInfo.DisplayNameComparator(context.getPackageManager()));

        final ArrayList<ApplicationInfo> filteredApplicationInfoList = new ArrayList<>();
        for (ApplicationInfo applicationInfo : applicationInfoList) {
            if (context.getPackageManager().getLaunchIntentForPackage(applicationInfo.packageName) == null
                    || applicationInfo.packageName.equals(context.getPackageName())) {
                continue;
            }

            filteredApplicationInfoList.add(applicationInfo);
        }

        return filteredApplicationInfoList;
    }
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }
    public static String getKeyHash(final Context context) {

        String packageName = context.getPackageName();
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return android.util.Base64.encodeToString(md.digest(), android.util.Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                //Log.w(TAG, "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }

}

