package com.example.hayden.heritagemalta2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScanner extends AppCompatActivity implements   ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
        QRScanner();

    }
    public void QRScanner(){
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }
    @Override
    public void onPause(){
        super.onPause();
        // mScannerView.stopCamera();
    }
    @Override
    public void handleResult(Result rawResult){

        ContentfulConnection conn = new ContentfulConnection(rawResult.toString());
        GetAudioTourTask tsk = new GetAudioTourTask(conn);
        tsk.execute();
        

    }
    class GetAudioTourTask extends AsyncTask<String,Void,String> {
        private ContentfulConnection conn;
        GetAudioTourTask(ContentfulConnection c){

            this.conn = c;
        }
        @Override
        protected String doInBackground(String... params) {
            conn.GetContent();
            try{Thread.sleep(1000);}
            catch(Exception e){}
            return conn.jResult;
        }

        @Override
        protected void onPostExecute(String s) {
            if(conn.jResult!=null){

                Context context = getApplicationContext();
                Intent intent = new Intent(context,AudioTourContent.class);
                intent.putExtra("ITEM_EXTRA",conn.jResult);
                startActivity(intent);
            }
            else{

                Context context = getApplicationContext();
                Intent intent  = new Intent(context,MainActivity.class);
                startActivity(intent);
            }
        }
    }

    }
