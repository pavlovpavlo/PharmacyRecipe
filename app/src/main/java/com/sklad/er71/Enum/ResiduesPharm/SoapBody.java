
package com.sklad.er71.Enum.ResiduesPharm;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SoapBody {

    @SerializedName("m:ResiduesPharmResponse")
    @Expose
    private MResiduesPharmResponse mResiduesPharmResponse;

    public MResiduesPharmResponse getmResiduesPharmResponse() {
        return mResiduesPharmResponse;
    }

    public void setmResiduesPharmResponse(MResiduesPharmResponse mResiduesPharmResponse) {
        this.mResiduesPharmResponse = mResiduesPharmResponse;
    }

}
