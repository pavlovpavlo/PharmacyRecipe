
package com.sklad.er71.Enum.ResiduesPharm;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MDrugstore {

    @SerializedName("m:id")
    @Expose
    private String mId;
    @SerializedName("m:name")
    @Expose
    private String mName;
    @SerializedName("m:p_address")
    @Expose
    private String mPAddress;
    @SerializedName("m:ogrn")
    @Expose
    private Long mOgrn;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPAddress() {
        return mPAddress;
    }

    public void setmPAddress(String mPAddress) {
        this.mPAddress = mPAddress;
    }

    public Long getmOgrn() {
        return mOgrn;
    }

    public void setmOgrn(Long mOgrn) {
        this.mOgrn = mOgrn;
    }

}
