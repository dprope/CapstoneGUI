package com.example.mortargui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import eu.amirs.JSON;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Sample_Activity extends AppCompatActivity {
    ImageView imageView;
    TextView textview;


    String url = "https://vmihci.herokuapp.com/getimage";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);
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
