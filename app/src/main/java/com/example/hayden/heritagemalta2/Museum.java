package com.example.hayden.heritagemalta2;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Hayden on 20/03/2017.
 */

public class Museum implements Parcelable {

     String Name;
     String Description;
     String PictureURL;
     String OpeningTime;
     String ClosingTime;
     String PhoneNumber;
     ArrayList<String> ClosedDates;
     String TicketPriceAdults;
     String TicketPriceYouths;
     String TicketPriceSeniors;
     String TicketPriceChildren;
     String TicketPriceInfants;
     String Latitude;
     String Longitude;

    public ArrayList<String> getClosedDates() {
        return ClosedDates;
    }

    public void setClosedDates(ArrayList<String> closedDates) {
        ClosedDates = closedDates;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getOpeningTime() {
        return OpeningTime;
    }

    public void setOpeningTime(String openingTime) {
        OpeningTime = openingTime;
    }

    public String getClosingTime() {
        return ClosingTime;
    }

    public void setClosingTime(String closingTime) {
        ClosingTime = closingTime;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }



    public String getTicketPriceAdults() {
        return TicketPriceAdults;
    }

    public void setTicketPriceAdults(String ticketPriceAdults) {
        TicketPriceAdults = ticketPriceAdults;
    }

    public String getTicketPriceYouths() {
        return TicketPriceYouths;
    }

    public void setTicketPriceYouths(String ticketPriceYouths) {
        TicketPriceYouths = ticketPriceYouths;
    }

    public String getTicketPriceSeniors() {
        return TicketPriceSeniors;
    }

    public void setTicketPriceSeniors(String ticketPriceSeniors) {
        TicketPriceSeniors = ticketPriceSeniors;
    }

    public String getTicketPriceChildren() {
        return TicketPriceChildren;
    }

    public void setTicketPriceChildren(String ticketPriceChildren) {
        TicketPriceChildren = ticketPriceChildren;
    }

    public String getTicketPriceInfants() {
        return TicketPriceInfants;
    }

    public void setTicketPriceInfants(String ticketPriceInfants) {
        TicketPriceInfants = ticketPriceInfants;
    }
    public String getPictureURL() {
        return PictureURL;
    }

    public void setPictureURL(String pictureURL) {
        PictureURL = pictureURL;
    }

    public void PopulateMuseum(JSONObject js){


    }

    public Museum() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Name);
        dest.writeString(this.Description);
        dest.writeString(this.PictureURL);
        dest.writeString(this.OpeningTime);
        dest.writeString(this.ClosingTime);
        dest.writeString(this.PhoneNumber);
        dest.writeStringList(this.ClosedDates);
        dest.writeString(this.TicketPriceAdults);
        dest.writeString(this.TicketPriceYouths);
        dest.writeString(this.TicketPriceSeniors);
        dest.writeString(this.TicketPriceChildren);
        dest.writeString(this.TicketPriceInfants);
        dest.writeString(this.Latitude);
        dest.writeString(this.Longitude);
    }

    protected Museum(Parcel in) {
        this.Name = in.readString();
        this.Description = in.readString();
        this.PictureURL = in.readString();
        this.OpeningTime = in.readString();
        this.ClosingTime = in.readString();
        this.PhoneNumber = in.readString();
        this.ClosedDates = in.createStringArrayList();
        this.TicketPriceAdults = in.readString();
        this.TicketPriceYouths = in.readString();
        this.TicketPriceSeniors = in.readString();
        this.TicketPriceChildren = in.readString();
        this.TicketPriceInfants = in.readString();
        this.Latitude = in.readString();
        this.Longitude = in.readString();
    }

    public static final Creator<Museum> CREATOR = new Creator<Museum>() {
        @Override
        public Museum createFromParcel(Parcel source) {
            return new Museum(source);
        }

        @Override
        public Museum[] newArray(int size) {
            return new Museum[size];
        }
    };
}
