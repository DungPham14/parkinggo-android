/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 13/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;

public class ParkingResponse {
    @SerializedName("message")
    @Expose
    String message;
    @SerializedName("data")
    @Expose
    RealmList<Parking> parkings;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RealmList<Parking> getParkings() {
        return parkings;
    }

    public void setParkings(RealmList<Parking> parkings) {
        this.parkings = parkings;
    }
}
