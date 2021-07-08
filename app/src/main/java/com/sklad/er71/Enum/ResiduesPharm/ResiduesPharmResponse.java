
package com.sklad.er71.Enum.ResiduesPharm;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResiduesPharmResponse {

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
