package com.example.hayden.heritagemalta2;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class AudioTourContent extends AppCompatActivity {
    AudioTour at = new AudioTour();
    AudioPlayer ap;
    boolean isPaused = true;
    Button PlayButton;
    TextView AudioTitle;
    TextView AudioDesc;
    ImageView AudioImage;
private Bitmap bmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_tour_content);

       /* try{
            JSONObject js = new JSONObject(getIntent().getStringExtra("ITEM_EXTRA"));
            at.PopulateAudioTour(js);
            ap = new AudioPlayer(at.AudioTourURL);
            PlayButton = (Button)findViewById(R.id.btnPlay);
            AudioDesc  = (TextView)findViewById(R.id.txtAudioDesc);
            AudioTitle = (TextView)findViewById(R.id.txtAudioTitle);
            ImageView img = (ImageView)findViewById(R.id.audioTourPic);
            AudioDesc.setText(at.AudioTourText);
            AudioTitle.setText(at.AudioTourTitle);
            AudioDesc.setMovementMethod(new ScrollingMovementMethod());
            Picasso.with(getApplicationContext()).load(at.AudioTourImageURL).into(img);
        }
        catch(JSONException e){

        }*/
    }
    public void PlayButtonClicked(View view){

        if(isPaused){

            Play();
            try{ Thread.sleep(300); }catch(InterruptedException e){ }
            PlayButton.setText("Pause");
            isPaused = false;
        }
        else{
            Pause();
            try{ Thread.sleep(300); }catch(InterruptedException e){ }
            PlayButton.setText("Play");
            isPaused = true;

        }

    }
    public void Play() {

        ap.StartAudio();

    }
    public void Pause(){

        ap.PauseAudio();
    }

    public void Stop(View view){
        isPaused = true;
        PlayButton.setText("Play");
        ap.StopAudio();
    }

}
