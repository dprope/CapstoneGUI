package com.example.mortargui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import eu.amirs.JSON;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Sample_Activity extends AppCompatActivity {
    ImageView imageView;
    TextView textview;



    //String url = "https://vmihci.herokuapp.com/getimage";

    String url ="http://144.75.191.68:5005/getimage";
    
    Button b1;
    private final static int SEND_SMS_PERMISSION_REQ=1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);
        
        b1 = findViewById(R.id.threatbutton);
        b1.setEnabled(false);
        
        if (checkPermission(Manifest.permission.SEND_SMS)) {
            b1.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQ);

        }
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String s2 = "POSSIBLE THREAT IN AREA";
                String s1 = "7706246972";
                String s3 = "3362072493";
                if (!TextUtils.isEmpty(s1) && !TextUtils.isEmpty(s2)) {
                    if (checkPermission(Manifest.permission.SEND_SMS)) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(s1, null, s2, null, null);
                        smsManager.sendTextMessage(s3, null, s2, null, null);

                    } else {
                        Toast.makeText(Sample_Activity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Sample_Activity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }


        });

        
      
        final Button nothreat = findViewById(R.id.nothreatbutt);
        final Intent intent = new Intent(this, Sample_Activity.class);
        nothreat.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
                startActivity(intent);


            }
        });
        final Button threat = findViewById(R.id.threatbutton);
        final Intent intent2 = new Intent(this, PhoneActivity.class);
        threat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });




        imageView = (ImageView) findViewById(R.id.imageee);
        loadImageFromUrl(url);
        textview = (TextView) findViewById(R.id.texx);

        String url2 = "https://vmihci.herokuapp.com/getrisk";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url2)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    Sample_Activity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //parse myreponse
                            JSON json = new JSON(myResponse);
                            String threat = json.key("threats").index(0).key("gunthreat").stringValue();
                            // String threat = json.key("gunthreat").stringValue();
                            //then put to textview
                            textview.setText("Risk: " + threat);
                        }
                    });
                }

            }

        });





    }
    
       private boolean checkPermission(String sendSms) {
        int check= ContextCompat.checkSelfPermission(this,sendSms);
        return check== PackageManager.PERMISSION_GRANTED;
    }
    
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults){
        switch(requestCode){
            case SEND_SMS_PERMISSION_REQ:
                if(grantResults.length>0 &&(grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                    b1.setEnabled(true);
                }
                break;
        }
    }

    private void loadImagefromURL_HTTP(String url){

        try{
            //String url = "http://172.22.30.55:8080/data/product_images/"+data.get("col_1");
            URL ulrn = new URL(url);
            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
            InputStream is = con.getInputStream();
            Bitmap bmp = BitmapFactory.decodeStream(is);
            if (null != bmp)
                imageView.setImageBitmap(bmp);
            else
                System.out.println("Bitmap is NULL");

            if (con != null) {
                con.disconnect();

            }
        } catch(Exception e) {
            //e.printStackTrace();
            Log.d("debug", e.getMessage());
        }
    }

    private void loadImageFromUrl(String url) {
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback() {


                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });



    }


}
