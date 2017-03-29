package parkinggo.com.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("token")
    @Expose
    String token;
    @SerializedName("message")
    @Expose
    String message;

}
