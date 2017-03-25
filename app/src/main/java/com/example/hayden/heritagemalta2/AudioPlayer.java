package com.example.hayden.heritagemalta2;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Hayden on 16/03/2017.
 */

public class AudioPlayer {

    private MediaPlayer mp;
    private boolean isPlaying;
    private boolean isPaused;
    private String MediaPlayerURL;

    //Constructor with initialisation
    AudioPlayer(String AudioURL) {
        InitialisePlayer(AudioURL);
    }
    //Default Constructor
    public AudioPlayer(){


    }
    //Creates a new audio player and sets its datasource for playing
    private void InitialisePlayer(String URL){
        mp = new MediaPlayer();
        try {
            mp.setDataSource(URL);
            MediaPlayerURL = URL;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //Prepares the audio for playback and then starts it.
     void StartAudio(){
        if(isPaused){

            mp.start();
            isPlaying = true;

        }
        else{mp.prepareAsync();
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mp.start();
                }
            });
            //If the media player has completed a track, re-initialise the player with the audio URL
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    InitialisePlayer(MediaPlayerURL);
                    isPaused = true;

                }
            });
            isPlaying = true;}


    }
    //Pauses the audio
    void PauseAudio(){

        if(isPlaying){

            mp.pause();

            isPlaying = false;
            isPaused = true;
        }

    }
    //Stops the audio and returns it to its original start point
    void StopAudio(){

        if(isPlaying || isPaused){

            mp.stop();
           // mp.prepareAsync();
            isPlaying = false;
            isPaused = false;
        }
    }
}
