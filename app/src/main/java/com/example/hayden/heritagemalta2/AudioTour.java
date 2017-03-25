package com.example.hayden.heritagemalta2;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hayden on 16/03/2017.
 */

public class AudioTour implements Parcelable {

    //The title in the AudioTour content type in Contentful
    String AudioTourTitle;
    //The AudioText field in the AudioTour content type in Contentful
    String AudioTourText;
    //The URL of the AudioFile field in the AudioTour content type in Contentful
    String AudioTourURL;

    String AudioTourImageURL;



    //region Setters and Getters
    public String getAudioTourText() {
        return AudioTourText;
    }

    public void setAudioTourText(String audioTourText) {
        AudioTourText = audioTourText;
    }

    public String getAudioTourURL() {
        return AudioTourURL;
    }

    public void setAudioTourURL(String audioTourURL) {
        AudioTourURL = audioTourURL;
    }
    public String getAudioTourTitle() {
        return AudioTourTitle;
    }

    public void setAudioTourTitle(String audioTourTitle) {
        AudioTourTitle = audioTourTitle;
    }
    public String getAudioTourImageURL() {
        return AudioTourImageURL;
    }

    public void setAudioTourImageURL(String audioTourImageURL) {
        AudioTourImageURL = audioTourImageURL;
    }
    //endregion

    //Grabs the required text from the Contentful JSON object and passes it into the properties of the AudioTourObject object

    //OLD CODE
   /* void PopulateAudioTour(JSONObject js){


        try{
            //Adds a 'http://' to the URL in order to allow a media player to play the file
            String s = new StringBuilder().append("http:").append(js.getJSONObject("fields").
                    getJSONObject("audioFile").
                    getJSONObject("en-GB").
                    getJSONObject("fields").
                    getJSONObject("file").
                    getJSONObject("en-GB").
                    getString("url")).toString();
            AudioTourURL = s;

            AudioTourText  = js.getJSONObject("fields").
                    getJSONObject("audioText").
                    getString("en-GB");
           AudioTourText = js.getJSONObject("fields").
                    getJSONObject("audioFile").
                    getJSONObject("en-GB").
                    getJSONObject("fields").
                    getJSONObject("description").
                    getString("en-GB");

           AudioTourTitle = js.getJSONObject("fields").
                    getJSONObject("audioFile").
                    getJSONObject("en-GB").
                    getJSONObject("fields").
                    getJSONObject("title").
                    getString("en-GB");
            String s2 = new StringBuilder().append("http:").append(js.getJSONObject("fields").
                    getJSONObject("imageTitle").
                    getJSONObject("en-GB").
                    getJSONObject("fields").
                    getJSONObject("file").
                    getJSONObject("en-GB").
                    getString("url")).toString();
            AudioTourImageURL =  s2;


        }
        catch(JSONException e){

            e.printStackTrace();
        }
    } */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AudioTourTitle);
        dest.writeString(this.AudioTourText);
        dest.writeString(this.AudioTourURL);
        dest.writeString(this.AudioTourImageURL);
    }

    public AudioTour() {
    }

    protected AudioTour(Parcel in) {
        this.AudioTourTitle = in.readString();
        this.AudioTourText = in.readString();
        this.AudioTourURL = in.readString();
        this.AudioTourImageURL = in.readString();
    }

    public static final Parcelable.Creator<AudioTour> CREATOR = new Parcelable.Creator<AudioTour>() {
        @Override
        public AudioTour createFromParcel(Parcel source) {
            return new AudioTour(source);
        }

        @Override
        public AudioTour[] newArray(int size) {
            return new AudioTour[size];
        }
    };
}
