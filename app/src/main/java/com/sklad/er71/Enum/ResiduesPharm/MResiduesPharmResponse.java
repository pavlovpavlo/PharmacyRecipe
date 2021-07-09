
package com.sklad.er71.Enum.ResiduesPharm;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class MResiduesPharmResponse implements Serializable {

    @SerializedName("xmlns:m")
    @Expose
    private String xmlnsM;
    @SerializedName("m:return")
    @Expose
    private MReturn mReturn;

    public String getXmlnsM() {
        return xmlnsM;
    }

    public void setXmlnsM(String xmlnsM) {
        this.xmlnsM = xmlnsM;
    }

    public MReturn getmReturn() {
        return mReturn;
    }

    public void setmReturn(MReturn mReturn) {
        this.mReturn = mReturn;
    }

}
