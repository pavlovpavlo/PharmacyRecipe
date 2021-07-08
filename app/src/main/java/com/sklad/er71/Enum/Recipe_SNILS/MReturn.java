
package com.sklad.er71.Enum.Recipe_SNILS;

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
    @SerializedName("m:tablerowrecipe")
    @Expose
    private List<MTablerowrecipe> mTablerowrecipe = null;

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

    public List<MTablerowrecipe> getmTablerowrecipe() {
        return mTablerowrecipe;
    }

    public void setmTablerowrecipe(List<MTablerowrecipe> mTablerowrecipe) {
        this.mTablerowrecipe = mTablerowrecipe;
    }

}
