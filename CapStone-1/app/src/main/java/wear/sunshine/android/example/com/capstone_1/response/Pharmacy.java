package wear.sunshine.android.example.com.capstone_1.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;

import java.util.Random;

import wear.sunshine.android.example.com.capstone_1.R;
import wear.sunshine.android.example.com.capstone_1.app.FindPharma;

/**
 * Created by jibin on 23/11/16.
 */

public class Pharmacy implements Parcelable{

    private String Name;

    private String Phone;
    public String imageUrl;
    private String Street;

    private String Region;

    private String TotalCount;

    private String Area;

    private String Latitude;

    private String Longitude;

    private String ClosingTime;

    private String Wilayat;

    private String StartingTime;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String Street) {
        this.Street = Street;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String Region) {
        this.Region = Region;
    }

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String TotalCount) {
        this.TotalCount = TotalCount;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getClosingTime() {
        return ClosingTime;
    }

    public void setClosingTime(String ClosingTime) {
        this.ClosingTime = ClosingTime;
    }

    public String getWilayat() {
        return Wilayat;
    }

    public void setWilayat(String Wilayat) {
        this.Wilayat = Wilayat;
    }

    public String getStartingTime() {
        return StartingTime;
    }

    public void setStartingTime(String StartingTime) {
        this.StartingTime = StartingTime;
    }

    public String getImageUrl(int pos) {
        imageUrl = FindPharma.imgUrl(true,pos);
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(Name);
        parcel.writeString(Phone);
        parcel.writeString(imageUrl);
        parcel.writeString(Street);
        parcel.writeString(Region);
        parcel.writeString(TotalCount);
        parcel.writeString(Area);
        parcel.writeString(Latitude);
        parcel.writeString( Longitude);
        parcel.writeString( ClosingTime);
        parcel.writeString( Wilayat);
        parcel.writeString( StartingTime);

    }
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Pharmacy createFromParcel(Parcel in) {
            return new Pharmacy(in);
        }

        public Pharmacy[] newArray(int size) {
            return new Pharmacy[size];
        }
    };


    public Pharmacy(Parcel in) {
        Name=in.readString();
        Phone=in.readString();
        imageUrl=in.readString();
        Street=in.readString();
        Region=in.readString();
        TotalCount=in.readString();
        Area=in.readString();
        Latitude=in.readString();
        Longitude=in.readString();
        ClosingTime=in.readString();
        Wilayat=in.readString();
        StartingTime=in.readString();
    }
}
