
package com.sklad.er71.Enum.ResiduesPharm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MReturn {

    @SerializedName("xmlns:xs")
    @Expose
    private String xmlnsXs;
    @SerializedName("xmlns:xsi")
    @Expose
    private String xmlnsXsi;
    @SerializedName("m:tablerowapp")
    @Expose
    private List<MTablerowapp> mTablerowapp = null;

    public String getXmlnsXs() {
        return xmlnsXs;
    }

    public void setXmlnsXs(String xmlnsXs) {
        this.xmlnsXs = xmlnsXs;
    }

    public String getXmlnsXsi() {
        return xmlnsXsi;
    }

    public void setXmlnsXsi(String xmlnsXsi) {
        this.xmlnsXsi = xmlnsXsi;
    }

    public List<MTablerowapp> getmTablerowapp() {
        return mTablerowapp;
    }

    public void setmTablerowapp(List<MTablerowapp> mTablerowapp) {
        this.mTablerowapp = mTablerowapp;
    }

}
