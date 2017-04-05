/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 04/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthenData {

    @SerializedName("token")
    @Expose
    String token;

    @SerializedName("user")
    @Expose
    User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
