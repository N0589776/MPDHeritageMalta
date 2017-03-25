package com.example.hayden.heritagemalta2;

/**
 * Created by Hayden on 16/03/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import android.os.AsyncTask;

import rx.Subscriber;
import rx.schedulers.Schedulers;
public class ContentfulConnection extends Activity {
    public String searchString;
    public String jResult;
    public CDAArray jArray;
    public ArrayList<Museum> Museums;
    public AudioTour at;


    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        searchString = searchString;
    }

    public ContentfulConnection(String input){


        searchString = input;

    }
    public ContentfulConnection(){


    }




    public CDAClient Initialise(){

        //Connection client that defines the Content Management System the app will connect to
        CDAClient client = CDAClient.builder()
                .setSpace("gh5rde2vse9c")
                .setToken("bd5820c235f825560148c75531e02fb120a4d672de857671031ea5c874cc1bd1")
                .build();
        //Checks if the connection is valid
        if(client != null){
            return client;
        }
        else{

            return null;
        }
    }
    public void testGetContent(){

       // final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //Initialises the client
        CDAClient client = Initialise();

        //Creates a new Audio Tour object to populate with CMS data
        at = new AudioTour();
        client.observe(CDAEntry.class)
                //Defines how many results the search aims to bring back
                .one(searchString)

                .subscribeOn(Schedulers.io())
                //Begins the search
                .subscribe(new Subscriber<CDAEntry>() {
                    CDAEntry result;

                    @Override public void onCompleted() {
                       // Log.i("Contentful", gson.toJson(result));
                        //Gets the AudioTour photo from the result
                       CDAAsset photo = result.getField("imageTitle");
                        Log.i("photo",photo.url());

                        //Gets the AudioTour audio from the result
                        CDAAsset audio = result.getField("audioFile");
                        Log.i("photo",audio.url());
                        //Gets the AudioTour title from the result
                        at.AudioTourTitle = result.getField("audioTitle");
                        at.AudioTourText = result.getField("audioText");

                        //Appends text to the AudioTour image to make it viewable
                        try{at.AudioTourImageURL = "http:" + photo.url();}
                        catch(Exception e){

                            Log.e("error",e.toString());

                        }
                        //Appends text to the AudioTour audio to make it listenable
                        try{at.AudioTourURL = "http:" + audio.url();}
                        catch(Exception e){Log.e("error",e.toString());}

                    }

                    @Override public void onError(Throwable error) {
                        Log.e("Contentful", "could not request entry", error);
                    }

                    @Override public void onNext(CDAEntry cdaEntry) {
                        result = cdaEntry;
                    }
                });
    }
   public void GetContent(){
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //Checks if a successful connection has been made
        if(Initialise() != null){
            CDAClient client = Initialise();


            client.observe(CDAEntry.class)
                    .one(searchString)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<CDAEntry>() {
                        CDAEntry result;
                        @Override
                        public void onCompleted() {//Log.i("Contentful",gson.toJson(result));
                            if(result != null){
                        CDAAsset photo = result.getField("audioImage");
                                CDAAsset audio = result.getField("audioFile");
                               at.AudioTourTitle = result.getField("audioTitle");
                                at.AudioTourText = result.getField("audioText");
                                try{at.AudioTourImageURL = "http:" + photo.url();}
                                catch(Exception e){
                                    Log.e("error",e.toString());
                                }
                               try{at.AudioTourURL = "http:" + audio.url();}
                               catch (Exception e ){

                                   Log.e("error",e.toString());
                               }
                            }



                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("Contentful","could not request entry",e);
                        }

                        @Override
                        public void onNext(CDAEntry cdaEntry) {
                            result = cdaEntry;
                        }


                    });}

        }

    public void testMultiContent(){
        //Initialises the client


        CDAClient client = Initialise();
        client.observe(CDAEntry.class)
                //Defines the conditions on the CMS data being brought back
                .where("content_type", "museum")
                //Defines how many results the search will bring back
                .all()
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CDAArray>() {
                    CDAArray result;

                    @Override
                    public void onCompleted() {
                        //Instantiates the Museums list
                         Museums = new ArrayList<Museum>();
                        //Loops through all the results brought back by the CMS
                        for (CDAResource resource : result.items()) {
                            CDAEntry entry = (CDAEntry) resource;
                            //Gets the photo asset from the CMS result
                           CDAAsset photo = entry.getField("museumPicture");

                            Museum mus = new Museum();
                            //Gets the details from the CMS result

                            mus.Name = entry.getField("museumName");
                            mus.Description = entry.getField("museumDescription");
                            mus.OpeningTime = entry.getField("openingTime");
                            mus.ClosingTime = entry.getField("closingTime");
                            mus.PhoneNumber = entry.getField("phoneNumber");
                            try{mus.ClosedDates = entry.getField("closedDates");}
                            catch(Exception e){
                                Log.e("Contentful",e.toString());
                            }
                            mus.TicketPriceAdults = entry.getField("adultTicketPrice");
                            mus.TicketPriceYouths = entry.getField("youthTicketPrice");
                            mus.TicketPriceSeniors = entry.getField("seniorsTicketPrice");
                            mus.TicketPriceChildren = entry.getField("childrenTicketPrice");
                            mus.TicketPriceInfants = entry.getField("infantsTicketPrice");
                            mus.Longitude = entry.getField("longitude");
                            mus.Latitude = entry.getField("latitude");
                            //Appends the Picture URL string to make it viewable
                          try{mus.PictureURL = "http:" +  photo.url();}
                          catch(Exception e){Log.e("error",e.toString());}


                            try{Museums.add(mus);}
                            catch(Exception e){
                                Log.e("error",e.toString());
                            }



                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e("Contentful", "could not request entry", error);
                    }

                    @Override
                    public void onNext(CDAArray cdaArray) {
                        result = cdaArray;
                    }
                });
    }
    /*public void GetMultiContent(){
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //Checks if a successful connection has been made
        if(Initialise() != null){
            CDAClient client = Initialise();


            client.observe(CDAEntry.class)
                    .where("content_type","museum")
                    .all()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<CDAArray>() {
                        CDAArray result;
                        @Override
                        public void onCompleted() {

                            if(result != null){
                                Museums = new List<Museum>() {
                                    @Override
                                    public int size() {
                                        return 0;
                                    }

                                    @Override
                                    public boolean isEmpty() {
                                        return false;
                                    }

                                    @Override
                                    public boolean contains(Object o) {
                                        return false;
                                    }

                                    @NonNull
                                    @Override
                                    public Iterator<Museum> iterator() {
                                        return null;
                                    }

                                    @NonNull
                                    @Override
                                    public Object[] toArray() {
                                        return new Object[0];
                                    }

                                    @NonNull
                                    @Override
                                    public <T> T[] toArray(T[] a) {
                                        return null;
                                    }

                                    @Override
                                    public boolean add(Museum museum) {
                                        return false;
                                    }

                                    @Override
                                    public boolean remove(Object o) {
                                        return false;
                                    }

                                    @Override
                                    public boolean containsAll(Collection<?> c) {
                                        return false;
                                    }

                                    @Override
                                    public boolean addAll(Collection<? extends Museum> c) {
                                        return false;
                                    }

                                    @Override
                                    public boolean addAll(int index, Collection<? extends Museum> c) {
                                        return false;
                                    }

                                    @Override
                                    public boolean removeAll(Collection<?> c) {
                                        return false;
                                    }

                                    @Override
                                    public boolean retainAll(Collection<?> c) {
                                        return false;
                                    }

                                    @Override
                                    public void clear() {

                                    }

                                    @Override
                                    public Museum get(int index) {
                                        return null;
                                    }

                                    @Override
                                    public Museum set(int index, Museum element) {
                                        return null;
                                    }

                                    @Override
                                    public void add(int index, Museum element) {

                                    }

                                    @Override
                                    public Museum remove(int index) {
                                        return null;
                                    }

                                    @Override
                                    public int indexOf(Object o) {
                                        return 0;
                                    }

                                    @Override
                                    public int lastIndexOf(Object o) {
                                        return 0;
                                    }

                                    @Override
                                    public ListIterator<Museum> listIterator() {
                                        return null;
                                    }

                                    @NonNull
                                    @Override
                                    public ListIterator<Museum> listIterator(int index) {
                                        return null;
                                    }

                                    @NonNull
                                    @Override
                                    public List<Museum> subList(int fromIndex, int toIndex) {
                                        return null;
                                    }
                                };
                                for(CDAResource resource : result.items()){
                                    CDAEntry entry = (CDAEntry) resource;
                                    Museum mus = new Museum();

                                    mus.Name = entry.getField("museumName");
                                    mus.Description = entry.getField("museumDescription");
                                    mus.OpeningTime = entry.getField("openingTime");
                                    mus.ClosingTime = entry.getField("closingTime");
                                    mus.PhoneNumber = entry.getField("phoneNumber");
                                    mus.ClosedDates = entry.getField("closedDates");
                                    mus.TicketPriceAdults = entry.getField("adultTicketPrice");
                                    mus.TicketPriceYouths = entry.getField("youthTicketPrice");
                                    mus.TicketPriceSeniors = entry.getField("seniorsTicketPrice");
                                    mus.TicketPriceChildren = entry.getField("childrenTicketPrice");
                                    mus.TicketPriceInfants = entry.getField("infantsTicketPrice");
                                    Log.i("Contentful",gson.toJson(entry));
                                    Museums.add(mus);
                                }

                            }



                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("Contentful","could not request entry",e);
                        }

                        @Override
                        public void onNext(CDAArray cdaArray) {
                            result = cdaArray;

                        }
                    });
        }

    } */



}


