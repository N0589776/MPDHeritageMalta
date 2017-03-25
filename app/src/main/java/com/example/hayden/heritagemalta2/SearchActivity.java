package com.example.hayden.heritagemalta2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class SearchActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
final Runnable r = new Runnable() {
    @Override
    public void run() {
        ContentfulConnection conn = new ContentfulConnection("thing");

        conn.Initialise();
        try{ Thread.sleep(300); }catch(InterruptedException e){ }
        conn.testMultiContent();
        try{ Thread.sleep(700); }catch(InterruptedException e){ }
        List<Museum> museums = conn.Museums;
        for (Museum m : museums
                ) {
            Log.i("Museum",m.Name);

        }
    }


};
        Thread ContentfulThread = new Thread(r);
        ContentfulThread.start();
    }
}
