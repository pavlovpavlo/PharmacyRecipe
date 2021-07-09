
package com.sklad.er71.Enum.ResiduesPharm;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class MFias  implements Serializable {

    @SerializedName("m:code")
    @Expose
    private String mCode;
    @SerializedName("m:address")
    @Expose
    private String mAddress;

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

}
