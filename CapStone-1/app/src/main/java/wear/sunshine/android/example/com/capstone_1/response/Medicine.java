package wear.sunshine.android.example.com.capstone_1.response;

import android.os.Parcel;
import android.os.Parcelable;

import wear.sunshine.android.example.com.capstone_1.app.FindPharma;

/**
 * Created by jibin on 23/11/16.
 */

public class Medicine implements Parcelable{

        private String PackSize;
         public String imageUrl;
        private String TradeName;

        private String TotalCount;

        private String Reg_No;

        private String Active2;

        private String ClosingTime;

        private String Origin;

        private String Active3;

        private String Company;

        private String LocalAgent;

        private String Active1;

        private String Rate;

        public String getPackSize ()
        {
            return PackSize;
        }

        public void setPackSize (String PackSize)
        {
            this.PackSize = PackSize;
        }

        public String getTradeName ()
        {
            return TradeName;
        }

        public void setTradeName (String TradeName)
        {
            this.TradeName = TradeName;
        }

        public String getTotalCount ()
        {
            return TotalCount;
        }

        public void setTotalCount (String TotalCount)
        {
            this.TotalCount = TotalCount;
        }

        public String getReg_No ()
        {
            return Reg_No;
        }

        public void setReg_No (String Reg_No)
        {
            this.Reg_No = Reg_No;
        }

        public String getActive2 ()
        {
            return Active2;
        }

        public void setActive2 (String Active2)
        {
            this.Active2 = Active2;
        }

        public String getClosingTime ()
        {
            return ClosingTime;
        }

        public void setClosingTime (String ClosingTime)
        {
            this.ClosingTime = ClosingTime;
        }

        public String getOrigin ()
        {
            return Origin;
        }

        public void setOrigin (String Origin)
        {
            this.Origin = Origin;
        }

        public String getActive3 ()
        {
            return Active3;
        }

        public void setActive3 (String Active3)
        {
            this.Active3 = Active3;
        }

        public String getCompany ()
        {
            return Company;
        }

        public void setCompany (String Company)
        {
            this.Company = Company;
        }

        public String getLocalAgent ()
        {
            return LocalAgent;
        }

        public void setLocalAgent (String LocalAgent)
        {
            this.LocalAgent = LocalAgent;
        }

        public String getActive1 ()
        {
            return Active1;
        }

        public void setActive1 (String Active1)
        {
            this.Active1 = Active1;
        }

        public String getRate ()
        {
            return Rate;
        }

        public void setRate (String Rate)
        {
            this.Rate = Rate;
        }

    public String getImageUrl(int pos) {
        imageUrl = FindPharma.imgUrl(false,pos);
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

        parcel.writeString(PackSize);
        parcel.writeString(imageUrl);
        parcel.writeString(TradeName);
        parcel.writeString(TotalCount);
        parcel.writeString(Reg_No);
        parcel.writeString(Active2);
        parcel.writeString(ClosingTime);
        parcel.writeString(Origin);
        parcel.writeString(Active3);
        parcel.writeString(Company);
        parcel.writeString(LocalAgent);
        parcel.writeString(Active1);

    }
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Medicine createFromParcel(Parcel in) {
            return new Medicine(in);
        }

        public Medicine[] newArray(int size) {
            return new Medicine[size];
        }
    };


    public Medicine(Parcel in) {
        PackSize= in.readString();
        imageUrl=in.readString();
        TradeName= in.readString();
        TotalCount=in.readString();
        Reg_No=in.readString();
        Active2=in.readString();
        ClosingTime= in.readString();
        Origin=in.readString();
        Active3=in.readString();
        Company=in.readString();
        LocalAgent=in.readString();
        Active1=in.readString();
    }
}
