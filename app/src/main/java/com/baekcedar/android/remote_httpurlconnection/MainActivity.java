package com.baekcedar.android.remote_httpurlconnection;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getApi();
            }
        });

    }

    private void getApi(){

         new AsyncTask<Void, Void, String>() {
            ProgressDialog progress;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress = new ProgressDialog(MainActivity.this);
                progress.setTitle("다운로드");
                progress.setMessage("download");
                progress.setProgressStyle((ProgressDialog.STYLE_SPINNER));
                progress.setCancelable(false);
                progress.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                StringBuffer sb = new  StringBuffer();
                try {
                    JSONObject json = new JSONObject(s);

                    JSONArray rows = json.getJSONArray("realtimeArrivalList");

                    int length = rows.length();
                    for(int i=0; i < length; i ++){
                        JSONObject result = (JSONObject) rows.get(i);
                        String trainName = result.getString("trainLineNm");
                        sb.append(trainName + "\n");

                    }

                }catch (Exception e ){}

                textView.setText(sb.toString());
                progress.dismiss();
            }

            @Override
            protected String doInBackground(Void... params) {
                String result = "";
                try {
                    result = Remote.getData("http://swopenapi.seoul.go.kr/api/subway/sample/json/realtimeStationArrival/0/5/%EC%84%9C%EC%9A%B8");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return result;
            }
        }.execute();
    }


}
