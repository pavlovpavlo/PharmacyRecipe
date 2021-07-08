
package com.sklad.er71.Enum.Recipe_SNILS;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MGetRecipeSNILSResponse {

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
