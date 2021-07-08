
package com.sklad.er71.Enum.ResiduesPharm;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SoapEnvelope {

    @SerializedName("xmlns:soap")
    @Expose
    private String xmlnsSoap;
    @SerializedName("soap:Body")
    @Expose
    private SoapBody soapBody;

    public String getXmlnsSoap() {
        return xmlnsSoap;
    }

    public void setXmlnsSoap(String xmlnsSoap) {
        this.xmlnsSoap = xmlnsSoap;
    }

    public SoapBody getSoapBody() {
        return soapBody;
    }

    public void setSoapBody(SoapBody soapBody) {
        this.soapBody = soapBody;
    }

}
