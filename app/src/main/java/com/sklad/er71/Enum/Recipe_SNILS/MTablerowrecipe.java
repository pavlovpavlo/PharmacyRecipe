
package com.sklad.er71.Enum.Recipe_SNILS;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class MTablerowrecipe implements Serializable {

    @SerializedName("m:data")
    @Expose
    private String mData;
    @SerializedName("m:series")
    @Expose
    private Integer mSeries;
    @SerializedName("m:number")
    @Expose
    private Integer mNumber;
    @SerializedName("m:MNN")
    @Expose
    private String mMNN;
    @SerializedName("m:Trademark")
    @Expose
    private String mTrademark;
    @SerializedName("m:Dosage")
    @Expose
    private String mDosage;
    @SerializedName("m:LF")
    @Expose
    private String mLF;
    @SerializedName("m:Pack")
    @Expose
    private String mPack;
    @SerializedName("m:Status")
    @Expose
    private Integer mStatus;
    @SerializedName("m:LPU")
    @Expose
    private String mLPU;
    @SerializedName("m:Doctor")
    @Expose
    private String mDoctor;
    @SerializedName("m:kol_v")
    @Expose
    private Integer mKolV;
    @SerializedName("m:QRString")
    @Expose
    private String mQRString;

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }

    public Integer getmSeries() {
        return mSeries;
    }

    public void setmSeries(Integer mSeries) {
        this.mSeries = mSeries;
    }

    public Integer getmNumber() {
        return mNumber;
    }

    public void setmNumber(Integer mNumber) {
        this.mNumber = mNumber;
    }

    public String getmMNN() {
        return mMNN;
    }

    public void setmMNN(String mMNN) {
        this.mMNN = mMNN;
    }

    public String getmTrademark() {
        return mTrademark;
    }

    public void setmTrademark(String mTrademark) {
        this.mTrademark = mTrademark;
    }

    public String getmDosage() {
        return mDosage;
    }

    public void setmDosage(String mDosage) {
        this.mDosage = mDosage;
    }

    public String getmLF() {
        return mLF;
    }

    public void setmLF(String mLF) {
        this.mLF = mLF;
    }

    public String getmPack() {
        return mPack;
    }

    public void setmPack(String mPack) {
        this.mPack = mPack;
    }

    public Integer getmStatus() {
        return mStatus;
    }

    public void setmStatus(Integer mStatus) {
        this.mStatus = mStatus;
    }

    public String getmLPU() {
        return mLPU;
    }

    public void setmLPU(String mLPU) {
        this.mLPU = mLPU;
    }

    public String getmDoctor() {
        return mDoctor;
    }

    public void setmDoctor(String mDoctor) {
        this.mDoctor = mDoctor;
    }

    public Integer getmKolV() {
        return mKolV;
    }

    public void setmKolV(Integer mKolV) {
        this.mKolV = mKolV;
    }

    public String getmQRString() {
        return mQRString;
    }

    public void setmQRString(String mQRString) {
        this.mQRString = mQRString;
    }

    @Override
    public String toString() {
        return "MTablerowrecipe{" +
                "mData='" + mData + '\'' +
                ", mSeries=" + mSeries +
                ", mNumber=" + mNumber +
                ", mMNN='" + mMNN + '\'' +
                ", mTrademark='" + mTrademark + '\'' +
                ", mDosage='" + mDosage + '\'' +
                ", mLF='" + mLF + '\'' +
                ", mPack='" + mPack + '\'' +
                ", mStatus=" + mStatus +
                ", mLPU='" + mLPU + '\'' +
                ", mDoctor='" + mDoctor + '\'' +
                ", mKolV=" + mKolV +
                ", mQRString='" + mQRString + '\'' +
                '}';
    }
}
