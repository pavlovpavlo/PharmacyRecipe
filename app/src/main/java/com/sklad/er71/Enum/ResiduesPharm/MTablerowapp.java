
package com.sklad.er71.Enum.ResiduesPharm;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class MTablerowapp implements Serializable {

    @SerializedName("m:drugstore")
    @Expose
    private MDrugstore mDrugstore;
    @SerializedName("m:drugstore_x")
    @Expose
    private Double mDrugstoreX;
    @SerializedName("m:drugstore_y")
    @Expose
    private Double mDrugstoreY;
    @SerializedName("m:fias")
    @Expose
    private MFias mFias;
    @SerializedName("m:TN")
    @Expose
    private String mTN;
    @SerializedName("m:LF")
    @Expose
    private String mLF;
    @SerializedName("m:dosage")
    @Expose
    private String mDosage;
    @SerializedName("m:k_so")
    @Expose
    private Integer mKSo;

    public MDrugstore getmDrugstore() {
        return mDrugstore;
    }

    public void setmDrugstore(MDrugstore mDrugstore) {
        this.mDrugstore = mDrugstore;
    }

    public Double getmDrugstoreX() {
        return mDrugstoreX;
    }

    public void setmDrugstoreX(Double mDrugstoreX) {
        this.mDrugstoreX = mDrugstoreX;
    }

    public Double getmDrugstoreY() {
        return mDrugstoreY;
    }

    public void setmDrugstoreY(Double mDrugstoreY) {
        this.mDrugstoreY = mDrugstoreY;
    }

    public MFias getmFias() {
        return mFias;
    }

    public void setmFias(MFias mFias) {
        this.mFias = mFias;
    }

    public String getmTN() {
        return mTN;
    }

    public void setmTN(String mTN) {
        this.mTN = mTN;
    }

    public String getmLF() {
        return mLF;
    }

    public void setmLF(String mLF) {
        this.mLF = mLF;
    }

    public String getmDosage() {
        return mDosage;
    }

    public void setmDosage(String mDosage) {
        this.mDosage = mDosage;
    }

    public Integer getmKSo() {
        return mKSo;
    }

    public void setmKSo(Integer mKSo) {
        this.mKSo = mKSo;
    }

    @Override
    public String toString() {
        return "MTablerowapp{" +
                "mDrugstore=" + mDrugstore +
                ", mDrugstoreX=" + mDrugstoreX +
                ", mDrugstoreY=" + mDrugstoreY +
                ", mFias=" + mFias +
                ", mTN='" + mTN + '\'' +
                ", mLF='" + mLF + '\'' +
                ", mDosage='" + mDosage + '\'' +
                ", mKSo=" + mKSo +
                '}';
    }
}
