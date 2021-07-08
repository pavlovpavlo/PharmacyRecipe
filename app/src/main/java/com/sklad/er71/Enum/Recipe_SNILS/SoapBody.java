
package com.sklad.er71.Enum.Recipe_SNILS;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SoapBody {

    @SerializedName("m:GetRecipe_SNILSResponse")
    @Expose
    private MGetRecipeSNILSResponse mGetRecipeSNILSResponse;

    public MGetRecipeSNILSResponse getmGetRecipeSNILSResponse() {
        return mGetRecipeSNILSResponse;
    }

    public void setmGetRecipeSNILSResponse(MGetRecipeSNILSResponse mGetRecipeSNILSResponse) {
        this.mGetRecipeSNILSResponse = mGetRecipeSNILSResponse;
    }

}
