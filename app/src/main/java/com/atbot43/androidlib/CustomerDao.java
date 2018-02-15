package com.atbot43.androidlib;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mrdevxus on 1/12/18.
 */

public class CustomerDao {

    @SerializedName("cardNo")
    private String cardNo;

    public String getCardNo() {
        return cardNo;
    }
}
