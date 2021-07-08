
package com.sklad.er71.Enum.Recipe_SNILS;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RecipeSNILSResponse {

    @SerializedName("soap:Envelope")
    @Expose
    private SoapEnvelope soapEnvelope;

    public SoapEnvelope getSoapEnvelope() {
        return soapEnvelope;
    }

    public void setSoapEnvelope(SoapEnvelope soapEnvelope) {
        this.soapEnvelope = soapEnvelope;
    }

}
