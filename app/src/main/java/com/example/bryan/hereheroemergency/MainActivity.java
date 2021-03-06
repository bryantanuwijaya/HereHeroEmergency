package com.example.bryan.hereheroemergency;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    static final int REQUEST_LOCATION = 1;
    static final int REQUEST_CALL =2;
    LocationManager locationManager;

    Boolean police=false;
    Boolean medic=false;


    double locationLongitude;
    double locationLatitude;
    String exactLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        final String number = "911";

        SharedPreferences prefs = getSharedPreferences("mydata",MODE_PRIVATE);
        final String nameDBdefault = prefs.getString("Full Name","");
        final String dateDBdefault = prefs.getString("Date of Birth","Date of Birth");
        final String addressDBdefault = prefs.getString("Address","");
        int bloodtypeint = prefs.getInt("spinnerSelection",0);
        final String bloodtype;
        bloodtype = SpinnerBloodtype(bloodtypeint);
        final int container = (prefs.getInt("Phone Number",0));

        final Button panicButton = findViewById(R.id.button_MainActivity_Panic);
        final Button infoButton = findViewById(R.id.info_button);
        final Button accountButton = findViewById(R.id.button_MainActivity_Account);
        final Button button_Siren = findViewById(R.id.button_MainActivity_Siren);
        final Button button_Report = findViewById(R.id.button_MainActivity_ReportEmergency);
        final Button button_ListNumber = findViewById(R.id.button_MainActivity_ListNumber);
        final CheckBox policeCheckBox = findViewById(R.id.police_checkBox);
        final CheckBox medicCheckBox = findViewById(R.id.medic_checkBox);


        button_Siren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),SirenActivity.class);
                startActivity(intent);
            }
        });

        policeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(policeCheckBox.isChecked())
                {
                    police=true;
                }
                else
                {
                    police=false;
                }
            }
        });

        medicCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(medicCheckBox.isChecked())
                {
                    medic =true;
                }
                else
                {
                    medic =false;
                }

            }
        });
        locationManager= (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location();

        panicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(exactLocation==null)
                {
                    Toast.makeText(MainActivity.this, "Make Sure Location is On and Try Again",Toast.LENGTH_LONG).show();
                }
                else{
                    final String panicID = UUID.randomUUID().toString();
                    String location = exactLocation;
                    Panic panic = new Panic(panicID,nameDBdefault,dateDBdefault,addressDBdefault,bloodtype,container,location,police,medic);
                    addPanicToDatabase(panicID, panic);
                }

            }

        });
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(intent);
            }


        });

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(intent);
            }


        });


        button_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), ReportActivity.class);
                startActivity(intent);
            }
        });

        button_ListNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),ListNumbersActivity.class);
                startActivity(intent);
            }
        });


    }

    @NonNull
    private String SpinnerBloodtype(int bloodtypeint) {
        String bloodtype;
        if(bloodtypeint == 1)
        {
            bloodtype = "A+";
        }
        else if (bloodtypeint ==2)
        {
            bloodtype = "A-";
        }
        else if (bloodtypeint == 3)
        {
            bloodtype = "B+";
        }
        else if (bloodtypeint == 4)
        {
            bloodtype = "AB+";
        }
        else if (bloodtypeint == 5)
        {
            bloodtype = "AB-";
        }
        else if (bloodtypeint == 6)
        {
            bloodtype = "O+";
        }
        else if (bloodtypeint == 7)
        {
            bloodtype = "O-";
        }
        else
        {
            bloodtype = "bloodtype";
        }
        return bloodtype;
    }

    void Location(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);

        }else
        {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location != null){
                locationLatitude = location.getLatitude();
                locationLongitude = location.getLongitude();
                exactLocation = locationLatitude+","+locationLongitude;
                Log.d(TAG, "getLatitude: " + locationLatitude);
                Log.d(TAG, "getLongitude: " + locationLongitude);
            }else
            {
                Toast.makeText(MainActivity.this, "No Location was Found",Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode){
            case REQUEST_LOCATION:
                Location();
                break;
        }
    }

    private void addPanicToDatabase(final String panicID, final Panic newPanic)
    {
        FirebaseFirestore.getInstance().collection(Constants.PANIC)
                .document(panicID)
                .set(newPanic)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Failed to send request", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onFailure: "+ e);
                    }
                });
    }

    public void button_MainActivity_Call(View view) {
        String number = "96124606";
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            Toast.makeText(this,"Please Press Call Again",Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+number));
            startActivity(intent);
        }
    }
}
